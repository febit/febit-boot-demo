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
package org.febit.boot.demo.doggy.auth.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.demo.doggy.auth.AppAuth;
import org.febit.boot.demo.doggy.auth.config.AuthProps;
import org.febit.boot.demo.doggy.auth.dao.AccountDao;
import org.febit.boot.demo.doggy.auth.dao.AccountPermissionDao;
import org.febit.boot.demo.doggy.auth.model.AppAuthImpl;
import org.febit.boot.demo.doggy.auth.model.LoginForm;
import org.febit.boot.demo.doggy.auth.model.LoginVO;
import org.febit.boot.demo.doggy.auth.model.PasswordChangeForm;
import org.febit.boot.demo.doggy.auth.model.account.AccountVO;
import org.febit.boot.demo.doggy.jmodel.po.AccountPO;
import org.febit.boot.util.AuthErrors;
import org.febit.boot.util.Errors;
import org.febit.common.jwt.JwtCodec;
import org.febit.lang.protocol.BusinessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    final AppAuth auth;

    final AuthProps props;
    final JwtCodec jwtCodec;

    final AccountDao accountDao;
    final AccountCurd accountCurd;
    final AccountPermissionDao accountPermissionDao;

    private static class BCryptPasswordEncoderLazyHolder {
        private static final BCryptPasswordEncoder INSTANCE = new BCryptPasswordEncoder();
    }

    public boolean checkPermissions(AppAuth auth, Collection<String> permissions) {
        return accountPermissionDao.checkPermissions(auth.identifier(), permissions);
    }

    public LoginVO login(LoginForm form) {
        var account = accountDao.findValidByUsername(form.getUsername());
        if (account == null || account.getPasswordHash() == null) {
            if (log.isDebugEnabled()) {
                if (account == null) {
                    log.debug("Unauthorized: account not found");
                } else if (account.getPasswordHash() == null) {
                    log.debug("Unauthorized: password not set");
                } else {
                    log.debug("Unauthorized: account not found and password not set");
                }
            }
            throw AuthErrors.UNAUTHORIZED
                    .exception("Invalid username or incorrect password");
        }

        verifyPassword(form.getPassword(), account.getPasswordHash());

        var expireAt = Instant.now().plusSeconds(props.getTokenExpireSeconds());
        String token;
        try {
            token = jwtCodec.encode(toJwtClaimsSet(account, expireAt));
        } catch (JOSEException e) {
            log.error("Failed to encode jwt token", e);
            throw Errors.SYSTEM.exception("Failed to encode jwt token");
        }
        return LoginVO.builder()
                .token(token)
                .expireAt(expireAt)
                .account(AccountVO.of(account))
                .build();
    }

    public void verifyPassword(String password, @Nullable String encryptedPassword) {
        var match = BCryptPasswordEncoderLazyHolder.INSTANCE
                .matches(password, encryptedPassword);

        if (!match) {
            if (log.isDebugEnabled()) {
                log.debug("Unauthorized: incorrect password");
            }
            throw AuthErrors.UNAUTHORIZED
                    .exception("Invalid username or incorrect password");
        }
    }

    public void changePassword(PasswordChangeForm form) {
        var account = accountCurd.require(auth.id());
        verifyPassword(form.getOldPassword(), account.getPasswordHash());

        var newHash = BCryptPasswordEncoderLazyHolder.INSTANCE
                .encode(form.getNewPassword());

        accountCurd.changePasswordHash(auth.id(), newHash);
    }

    public AppAuth fromJwtToken(String token) {
        var resp = jwtCodec.decode(token);
        if (resp.isFailed()) {
            if (AuthErrors.INVALID_TOKEN.name().equals(resp.getCode())) {
                log.debug("Invalid jwt token: {}", resp.getMessage());
                throw AuthErrors.INVALID_TOKEN
                        .exception("Invalid token");
            }
            throw BusinessException.from(resp);
        }

        var payload = resp.getData();
        Objects.requireNonNull(payload);
        return from(payload);
    }

    public AppAuth fromAccountCode(String code) {
        var account = accountDao.findValidByUsername(code);
        if (account == null) {
            throw AuthErrors.UNAUTHORIZED
                    .exception("Invalid account: {0}", code);
        }
        return from(account);
    }

    public AppAuth from(AccountPO account) {
        return AppAuthImpl.builder()
                .id(account.getId())
                .identifier(account.getUsername())
                .displayName(account.getDisplayName())
                .build();
    }

    public AppAuth from(JWTClaimsSet claims) {
        var code = claims.getSubject();
        return fromAccountCode(code);
    }

    public JWTClaimsSet toJwtClaimsSet(AccountPO account, Instant expireAt) {
        return new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issueTime(new Date())
                .expirationTime(Date.from(expireAt))
                .build();
    }
}
