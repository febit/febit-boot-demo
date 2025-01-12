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
package org.febit.boot.demo.doggy.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.febit.boot.demo.doggy.JsonApiMapping;
import org.febit.boot.demo.doggy.auth.AppAuth;
import org.febit.boot.demo.doggy.auth.model.AppAuthImpl;
import org.febit.boot.permission.AnonymousApi;
import org.febit.lang.protocol.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Common")
@JsonApiMapping({
        "/api/v1/common"
})
@RequiredArgsConstructor
public class CommonApi implements IBasicApi {

    private final AppAuth auth;

    @AnonymousApi
    @GetMapping(value = "/ping")
    public IResponse<String> ping() {
        return ok("pong");
    }

    @AnonymousApi
    @GetMapping(value = "/who-am-i")
    public IResponse<AppAuth> whoAmI() {
        return ok(
                AppAuthImpl.copyOf(auth)
        );
    }
}
