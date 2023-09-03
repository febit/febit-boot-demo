package org.febit.boot.demo.doggy.model.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginForm {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
