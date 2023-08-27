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
package org.febit.boot.demo.doggy.config.auth;

import org.apache.commons.lang3.StringUtils;
import org.febit.boot.common.util.AuthErrors;
import org.febit.boot.web.auth.WebRequestAuthSubjectResolver;
import org.febit.lang.annotation.NonNullApi;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@NonNullApi
@Component
public class DemoWebRequestAuthSubjectResolver implements WebRequestAuthSubjectResolver<DemoAuth> {

    private static final String PREFIX_FAKE = "Fake ";
    private static final String FAKE_FMT = "Fake authCode:displayName";

    private static final DemoAuth ANONYMOUS_AUTH = DemoAuthImpl.builder()
            .code(DemoPermissionVerifierImpl.ANONYMOUS)
            .displayName("Anonymous")
            .build();

    @Override
    public Optional<DemoAuth> resolveAuth(WebRequest request) {
        var token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || token.isEmpty()) {
            return Optional.of(ANONYMOUS_AUTH);
        }
        return Optional.of(parseAuth(token));
    }

    private DemoAuth parseAuth(String token) {
        if (!token.startsWith(PREFIX_FAKE)) {
            throw AuthErrors.UNSUPPORTED_GRANT_TYPE
                    .exception("Only support [Fake] token for now: {0}", FAKE_FMT);
        }
        var segment = StringUtils.split(
                StringUtils.removeStart(token, PREFIX_FAKE),
                ":", 2
        );
        if (segment.length != 2) {
            throw AuthErrors.INVALID_TOKEN
                    .exception("Invalid fake token, format should be: {0}", FAKE_FMT);
        }
        return DemoAuthImpl.builder()
                .code(segment[0])
                .displayName(segment[1])
                .build();
    }
}
