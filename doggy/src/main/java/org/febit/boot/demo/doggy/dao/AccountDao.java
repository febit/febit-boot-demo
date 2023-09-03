package org.febit.boot.demo.doggy.dao;

import jakarta.annotation.Nullable;
import org.febit.boot.demo.doggy.jmodel.po.AccountPO;
import org.febit.boot.demo.doggy.jmodel.record.AccountRecord;
import org.febit.boot.demo.doggy.jmodel.table.TAccount;
import org.febit.boot.demo.doggy.model.auth.AppAuth;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class AccountDao extends BaseExtraDao<TAccount, AccountPO, Long, AccountRecord> {

    public AccountDao(Configuration conf) {
        super(conf);
    }

    @Nullable
    public AccountPO findValidByUsername(String username) {
        return findBy(
                T.USERNAME.eq(username),
                T.ENABLED.isTrue()
        );
    }

    public void updatePasswordHash(Long id, String passwordHash, AppAuth auth) {
        update()
                .set(T.PASSWORD_HASH, passwordHash)
                .set(T.UPDATED_AT, Instant.now())
                .set(T.UPDATED_BY, auth.getCode())
                .where(
                        T.ID.eq(id)
                )
                .execute();
    }

}
