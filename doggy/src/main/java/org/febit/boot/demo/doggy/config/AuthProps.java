package org.febit.boot.demo.doggy.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.febit.boot.jwt.JwtCodecProps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "app.auth")
@RequiredArgsConstructor(onConstructor_ = @ConstructorBinding)
public class AuthProps {

    private final Integer tokenExpireSeconds;
    private final JwtCodecProps jwt;
}
