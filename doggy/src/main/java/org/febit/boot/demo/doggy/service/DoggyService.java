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
package org.febit.boot.demo.doggy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.common.util.Errors;
import org.febit.boot.demo.doggy.dao.DoggyDao;
import org.febit.boot.demo.doggy.jmodel.po.DoggyPO;
import org.febit.boot.demo.doggy.model.auth.DemoAuth;
import org.febit.boot.demo.doggy.model.doggy.DoggyCreateForm;
import org.febit.boot.demo.doggy.model.doggy.DoggySearchForm;
import org.febit.boot.demo.doggy.model.doggy.DoggyUpdateForm;
import org.febit.boot.demo.doggy.model.doggy.DoggyVO;
import org.febit.boot.demo.doggy.util.DoggyErrors;
import org.febit.lang.protocol.Page;
import org.febit.lang.protocol.Pagination;
import org.febit.lang.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoggyService {

    final DemoAuth auth;
    final DoggyDao dao;

    public DoggyPO require(Integer id) {
        var po = dao.findById(id);
        DoggyErrors.NOT_FOUND_DOGGY
                .whenNull(po, "doggies.not-found", id);
        assert po != null;
        return po;
    }

    public Page<DoggyVO> search(DoggySearchForm form, Pagination page) {
        return dao.page(page, form)
                .transfer(DoggyVO::of);
    }

    public List<DoggyVO> list(DoggySearchForm form) {
        var pos = dao.listBy(form);
        return Lists.collect(pos, DoggyVO::of);
    }

    public DoggyVO requireVoById(Integer id) {
        return DoggyVO.of(require(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Integer> ids) {
        dao.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    public DoggyVO update(Integer id, DoggyUpdateForm form) {
        var po = require(id);
        if (!po.isNameEquals(form)) {
            Errors.FORBIDDEN.whenFalse(
                    !dao.isNameUsed(form.getName()),
                    "doggies.name.exists", form.getName()
            );
        }
        form.to(po);
        po.updated(auth);
        dao.update(po);
        return DoggyVO.of(po);
    }

    @Transactional(rollbackFor = Exception.class)
    public DoggyVO create(DoggyCreateForm form) {
        Errors.FORBIDDEN.whenFalse(
                !dao.isNameUsed(form.getName()),
                "doggies.name.exists", form.getName()
        );
        var po = form.to(DoggyPO::new);
        po.created(auth);
        dao.insert(po);
        return DoggyVO.of(po);
    }
}

