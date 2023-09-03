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
import org.febit.boot.web.IBasicApi;
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
