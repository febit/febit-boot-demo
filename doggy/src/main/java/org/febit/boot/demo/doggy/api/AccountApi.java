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
package org.febit.boot.demo.doggy.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.demo.doggy.Permissions;
import org.febit.boot.demo.doggy.model.account.AccountCreateForm;
import org.febit.boot.demo.doggy.model.account.AccountSearchForm;
import org.febit.boot.demo.doggy.model.account.AccountUpdateForm;
import org.febit.boot.demo.doggy.model.account.AccountVO;
import org.febit.boot.demo.doggy.service.AccountCurd;
import org.febit.lang.protocol.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.febit.lang.protocol.Page;
import org.febit.lang.protocol.Pagination;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Accounts")
@RequestMapping(value = {
        "/api/v1/accounts"
}, produces = {
        MediaType.APPLICATION_JSON_VALUE
})
public class AccountApi implements IBasicApi {

    private final AccountCurd curd;

    @Permissions.Admin
    @GetMapping("/{id}")
    public IResponse<AccountVO> requireById(
            @PathVariable Long id
    ) {
        return ok(AccountVO.of(
                curd.require(id)
        ));
    }

    @Permissions.Admin
    @PostMapping("/list")
    public IResponse<List<AccountVO>> list(
            @RequestBody @Valid AccountSearchForm form
    ) {
        return ok(curd.list(form));
    }

    @Permissions.Admin
    @PostMapping("/search")
    public IResponse<Page<AccountVO>> search(
            Pagination page,
            @RequestBody @Valid AccountSearchForm form
    ) {
        return ok(curd.search(page, form));
    }

    @Permissions.Admin
    @PostMapping("")
    public IResponse<AccountVO> create(
            @RequestBody @Valid AccountCreateForm form
    ) {
        return ok(AccountVO.of(
                curd.create(form)
        ));
    }

    @Permissions.Admin
    @PutMapping("/{id}")
    public IResponse<AccountVO> update(
            @PathVariable Long id,
            @RequestBody @Valid AccountUpdateForm form
    ) {
        return ok(AccountVO.of(
                curd.update(id, form)
        ));
    }

    @Permissions.Admin
    @DeleteMapping("/{id}")
    public IResponse<Void> deleteById(
            @PathVariable Long id
    ) {
        curd.deleteById(id);
        return ok();
    }

    @Permissions.Admin
    @DeleteMapping("/by-ids/{ids}")
    public IResponse<Void> deleteByIds(
            @PathVariable List<Long> ids
    ) {
        curd.deleteByIds(ids);
        return ok();
    }
}
