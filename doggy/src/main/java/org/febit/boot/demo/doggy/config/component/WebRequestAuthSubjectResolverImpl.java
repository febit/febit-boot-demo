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
package org.febit.boot.demo.doggy.config.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.auth.WebRequestAuthSubjectResolver;
import org.febit.boot.common.util.AuthErrors;
import org.febit.boot.demo.doggy.config.Auths;
import org.febit.boot.demo.doggy.model.auth.AppAuth;
import org.febit.boot.demo.doggy.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.removeStart;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebRequestAuthSubjectResolverImpl implements WebRequestAuthSubjectResolver<AppAuth> {

    private static final String PREFIX_FAKE = "Fake ";
    private static final String PREFIX_BEARER = "Bearer ";

    private final AuthService authService;

    @Override
    public Optional<AppAuth> resolveAuth(WebRequest request) {
        var token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || token.isEmpty()) {
            return Optional.of(Auths.ANONYMOUS);
        }
        return Optional.of(parse(token));
    }

    private AppAuth parse(String token) {
        if (token.startsWith(PREFIX_BEARER)) {
            return parseBearer(
                    removeStart(token, PREFIX_BEARER)
            );
        }
        if (token.startsWith(PREFIX_FAKE)) {
            return parseFake(
                    removeStart(token, PREFIX_FAKE)
            );
        }
        throw AuthErrors.UNSUPPORTED_GRANT_TYPE
                .exception("Only support 'Fake' and 'Bearer' token for now");
    }

    private AppAuth parseBearer(String token) {
        return authService.fromJwtToken(token);
    }

    private AppAuth parseFake(String token) {
        return authService.fromAccountCode(token);
    }

}
