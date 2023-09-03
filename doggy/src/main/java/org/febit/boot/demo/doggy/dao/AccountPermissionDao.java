package org.febit.boot.demo.doggy.dao;

import org.febit.boot.demo.doggy.jmodel.Tables;
import org.febit.boot.demo.doggy.jmodel.po.AccountPermissionPO;
import org.febit.boot.demo.doggy.jmodel.record.AccountPermissionRecord;
import org.febit.boot.demo.doggy.jmodel.table.TAccountPermission;
import org.febit.boot.demo.doggy.model.auth.AppAuth;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class AccountPermissionDao extends BaseExtraDao<TAccountPermission, AccountPermissionPO, Long, AccountPermissionRecord> {

    public AccountPermissionDao(Configuration conf) {
        super(conf);
    }

    public boolean checkPermissions(String accountCode, Collection<String> permissions) {
        return existsBy(
                T.ACCOUNT_ID.eq(dsl().select(Tables.ACCOUNT.ID)
                        .from(Tables.ACCOUNT)
                        .where(Tables.ACCOUNT.USERNAME.eq(accountCode))
                ),
                T.CODE.in(permissions)
        );
    }

    public List<AccountPermissionPO> listByAccount(Long accountId) {
        return listBy(
                T.ACCOUNT_ID.eq(accountId)
        );
    }

    public void relate(Long accountId, Collection<String> permissions, AppAuth auth) {
        if (permissions.isEmpty()) {
            return;
        }
        var createdBy = auth.getCode();
        var insertDsl = insert()
                .columns(T.ACCOUNT_ID, T.CODE, T.CREATED_BY, T.UPDATED_BY);
        for (var code : permissions) {
            insertDsl = insertDsl.values(accountId, code, createdBy, createdBy);
        }
        insertDsl.onDuplicateKeyIgnore()
                .execute();
    }

    public void unrelate(Long accountId, Collection<String> permissions) {
        if (permissions.isEmpty()) {
            return;
        }
        deleteBy(
                T.ACCOUNT_ID.eq(accountId),
                T.CODE.in(permissions)
        );
    }

    private void unrelateByAccount(Long accountId) {
        deleteBy(
                T.ACCOUNT_ID.eq(accountId)
        );
    }

    private void unrelateExcludeRoles(Long accountId, Collection<String> permissions) {
        if (permissions.isEmpty()) {
            unrelateByAccount(accountId);
            return;
        }
        deleteBy(
                T.ACCOUNT_ID.eq(accountId),
                T.CODE.notIn(permissions)
        );
    }

    public void fullRelate(Long accountId, Collection<String> permissions, AppAuth auth) {
        // Insert first
        relate(accountId, permissions, auth);
        // Delete not in list
        unrelateExcludeRoles(accountId, permissions);
    }

}
