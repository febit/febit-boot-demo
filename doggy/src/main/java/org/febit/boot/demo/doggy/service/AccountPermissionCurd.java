package org.febit.boot.demo.doggy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.common.util.Errors;
import org.febit.boot.demo.doggy.dao.AccountPermissionDao;
import org.febit.boot.demo.doggy.jmodel.po.AccountPermissionPO;
import org.febit.boot.demo.doggy.model.account.AccountPermissionSearchForm;
import org.febit.boot.demo.doggy.model.account.AccountPermissionVO;
import org.febit.boot.demo.doggy.model.auth.AppAuth;
import org.febit.lang.protocol.Page;
import org.febit.lang.protocol.Pagination;
import org.febit.lang.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountPermissionCurd {

    private final AppAuth auth;

    private final AccountPermissionDao dao;

    public AccountPermissionPO require(Long id) {
        var entity = dao.findById(id);
        Errors.NOT_FOUND.whenNull(entity, "Not found account-permission: {0}", id);
        assert entity != null;
        return entity;
    }

    public List<String> listMyPermissions() {
        return Lists.collect(
                dao.listByAccount(auth.getId()),
                AccountPermissionPO::getCode
        );
    }

    public Page<AccountPermissionVO> search(Pagination page, AccountPermissionSearchForm form) {
        return dao.page(page, form)
                .transfer(AccountPermissionVO::of);
    }

    public List<AccountPermissionVO> list(AccountPermissionSearchForm form) {
        return Lists.collect(
                dao.listBy(form),
                AccountPermissionVO::of
        );
    }

    public void add(Long accountId, Set<String> permissions) {
        dao.relate(accountId, permissions, auth);
    }

    public void remove(Long accountId, Set<String> permissions) {
        dao.unrelate(accountId, permissions);
    }

    public void set(Long accountId, Set<String> permissions) {
        dao.fullRelate(accountId, permissions, auth);
    }

}
