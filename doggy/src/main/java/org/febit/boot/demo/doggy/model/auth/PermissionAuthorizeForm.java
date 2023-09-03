package org.febit.boot.demo.doggy.model.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class PermissionAuthorizeForm {

    @NotNull
    private Long accountId;

    @NotNull
    @Size(max = 1000)
    private Set<String> permissions;
}
