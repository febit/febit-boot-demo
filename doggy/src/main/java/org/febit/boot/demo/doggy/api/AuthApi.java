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
import org.febit.boot.common.permission.AnonymousApi;
import org.febit.boot.demo.doggy.Permissions;
import org.febit.boot.demo.doggy.model.account.AccountVO;
import org.febit.boot.demo.doggy.model.auth.LoginForm;
import org.febit.boot.demo.doggy.model.auth.LoginVO;
import org.febit.boot.demo.doggy.model.auth.PasswordChangeForm;
import org.febit.boot.demo.doggy.service.AccountCurd;
import org.febit.boot.demo.doggy.service.AccountPermissionCurd;
import org.febit.boot.demo.doggy.service.AuthService;
import org.febit.lang.protocol.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth API")
@RequestMapping(value = {
        "/api/v1/auth"
}, produces = {
        MediaType.APPLICATION_JSON_VALUE
})
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
