package org.febit.boot.demo.doggy.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.febit.boot.demo.doggy.model.account.AccountVO;

import java.time.Instant;

@Setter
@Getter
@lombok.Builder(
        builderClassName = "Builder"
)
public class LoginVO {

    private String token;
    private Instant expireAt;
    private AccountVO account;
}
