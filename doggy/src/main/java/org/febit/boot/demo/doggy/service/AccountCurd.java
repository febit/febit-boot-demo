package org.febit.boot.demo.doggy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.common.util.Errors;
import org.febit.boot.demo.doggy.dao.AccountDao;
import org.febit.boot.demo.doggy.jmodel.po.AccountPO;
import org.febit.boot.demo.doggy.model.account.AccountCreateForm;
import org.febit.boot.demo.doggy.model.account.AccountSearchForm;
import org.febit.boot.demo.doggy.model.account.AccountUpdateForm;
import org.febit.boot.demo.doggy.model.account.AccountVO;
import org.febit.boot.demo.doggy.model.auth.AppAuth;
import org.febit.lang.protocol.Page;
import org.febit.lang.protocol.Pagination;
import org.febit.lang.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountCurd {

    private final AppAuth auth;

    private final AccountDao dao;

    public AccountPO require(Long id) {
        var entity = dao.findById(id);
        Errors.NOT_FOUND.whenNull(entity, "Not found account: {0}", id);
        assert entity != null;
        return entity;
    }

    public AccountVO me() {
        return AccountVO.of(
                require(auth.getId())
        );
    }

    public Page<AccountVO> search(Pagination page, AccountSearchForm form) {
        return dao.page(page, form)
                .transfer(AccountVO::of);
    }

    public List<AccountVO> list(AccountSearchForm form) {
        return Lists.collect(
                dao.listBy(form),
                AccountVO::of
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public AccountPO create(AccountCreateForm form) {
        var entity = form.to(AccountPO::new);
        entity.created(auth);
        dao.insert(entity);
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    public AccountPO update(Long id, AccountUpdateForm form) {
        var entity = require(id);
        form.to(entity);
        entity.updated(auth);
        dao.update(entity);
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void changePasswordHash(Long id, String passwordHash) {
        dao.updatePasswordHash(id, passwordHash, auth);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> ids) {
        dao.deleteByIds(ids);
    }
}
