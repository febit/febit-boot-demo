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
package org.febit.boot.demo.doggy.auth.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.febit.boot.demo.doggy.JsonApiMapping;
import org.febit.boot.demo.doggy.Permissions;
import org.febit.boot.demo.doggy.auth.model.LoginForm;
import org.febit.boot.demo.doggy.auth.model.LoginVO;
import org.febit.boot.demo.doggy.auth.model.PasswordChangeForm;
import org.febit.boot.demo.doggy.auth.model.account.AccountVO;
import org.febit.boot.demo.doggy.auth.service.AccountCurd;
import org.febit.boot.demo.doggy.auth.service.AccountPermissionCurd;
import org.febit.boot.demo.doggy.auth.service.AuthService;
import org.febit.boot.permission.AnonymousApi;
import org.febit.lang.protocol.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Auth API")
@JsonApiMapping({
        "/api/v1/auth"
})
@RequiredArgsConstructor
public class AuthApi implements IBasicApi {

    private final AccountCurd accountCurd;
    private final AccountPermissionCurd accountPermissionCurd;

    private final AuthService service;

    @AnonymousApi
    @PostMapping(value = "/login")
    public IResponse<LoginVO> login(
            @RequestBody @Valid LoginForm form
    ) {
        return ok(
                service.login(form)
        );
    }

    @Permissions.Basic
    @PostMapping(value = "/change-password")
    public IResponse<Void> changePassword(
            @RequestBody @Valid PasswordChangeForm form
    ) {
        service.changePassword(form);
        return ok();
    }

    @AnonymousApi
    @GetMapping(value = "/me")
    public IResponse<AccountVO> me(
    ) {
        return ok(
                accountCurd.me()
        );
    }

    @AnonymousApi
    @GetMapping(value = "/me/permissions")
    public IResponse<Collection<String>> myPermissions() {
        return ok(
                accountPermissionCurd.listMyPermissions()
        );
    }

}
