/*
 * Copyright 2023-present febit.org (support@febit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.febit.boot.demo.doggy.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.demo.doggy.auth.AppAuth;
import org.febit.boot.demo.doggy.auth.dao.AccountPermissionDao;
import org.febit.boot.demo.doggy.auth.model.account.AccountPermissionSearchForm;
import org.febit.boot.demo.doggy.auth.model.account.AccountPermissionVO;
import org.febit.boot.demo.doggy.jmodel.po.AccountPermissionPO;
import org.febit.boot.util.Errors;
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
                dao.listByAccount(auth.id()),
                AccountPermissionPO::getCode
        );
    }

    public Page<AccountPermissionVO> search(Pagination page, AccountPermissionSearchForm form) {
        return dao.page(page, form)
                .map(AccountPermissionVO::of);
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
