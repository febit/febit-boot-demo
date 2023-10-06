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
import org.febit.boot.demo.doggy.JsonApiMapping;
import org.febit.boot.demo.doggy.Permissions;
import org.febit.boot.demo.doggy.model.account.AccountPermissionSearchForm;
import org.febit.boot.demo.doggy.model.account.AccountPermissionVO;
import org.febit.boot.demo.doggy.model.auth.PermissionAuthorizeForm;
import org.febit.boot.demo.doggy.service.AccountPermissionCurd;
import org.febit.lang.protocol.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.febit.lang.protocol.Page;
import org.febit.lang.protocol.Pagination;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Validated
@Tag(name = "Account Permissions")
@JsonApiMapping({
        "/api/v1/account-permissions"
})
@RequiredArgsConstructor
public class AccountPermissionApi implements IBasicApi {

    private final AccountPermissionCurd curd;

    @Permissions.Admin
    @GetMapping("/{id}")
    public IResponse<AccountPermissionVO> requireById(
            @PathVariable Long id
    ) {
        return ok(AccountPermissionVO.of(
                curd.require(id)
        ));
    }

    @Permissions.Admin
    @PostMapping("/list")
    public IResponse<List<AccountPermissionVO>> list(
            @RequestBody @Valid AccountPermissionSearchForm form
    ) {
        return ok(curd.list(form));
    }

    @Permissions.Admin
    @PostMapping("/search")
    public IResponse<Page<AccountPermissionVO>> search(
            Pagination page,
            @RequestBody @Valid AccountPermissionSearchForm form
    ) {
        return ok(curd.search(page, form));
    }

    @Permissions.Admin
    @PostMapping("/by-account/{accountId}/remove")
    public IResponse<Void> remove(
            @RequestBody @Valid PermissionAuthorizeForm form
    ) {
        curd.remove(form.getAccountId(), form.getPermissions());
        return ok();
    }

    @Permissions.Admin
    @PatchMapping("/by-account/{accountId}")
    public IResponse<Void> add(
            @RequestBody @Valid PermissionAuthorizeForm form
    ) {
        curd.add(form.getAccountId(), form.getPermissions());
        return ok();
    }

    @Permissions.Admin
    @PutMapping("/by-account/{accountId}")
    public IResponse<Void> set(
            @RequestBody @Valid PermissionAuthorizeForm form
    ) {
        curd.set(form.getAccountId(), form.getPermissions());
        return ok();
    }

}
