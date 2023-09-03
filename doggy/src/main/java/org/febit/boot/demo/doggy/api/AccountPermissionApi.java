package org.febit.boot.demo.doggy.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.demo.doggy.Permissions;
import org.febit.boot.demo.doggy.model.account.AccountPermissionSearchForm;
import org.febit.boot.demo.doggy.model.account.AccountPermissionVO;
import org.febit.boot.demo.doggy.model.auth.PermissionAuthorizeForm;
import org.febit.boot.demo.doggy.service.AccountPermissionCurd;
import org.febit.lang.protocol.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.febit.lang.protocol.Page;
import org.febit.lang.protocol.Pagination;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@Tag(name = "Account Permissions")
@RequestMapping(value = {
        "/api/v1/account-permissions"
}, produces = {
        MediaType.APPLICATION_JSON_VALUE
})
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
