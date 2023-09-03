package org.febit.boot.demo.doggy.model.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordChangeForm {

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String newPassword;
}
