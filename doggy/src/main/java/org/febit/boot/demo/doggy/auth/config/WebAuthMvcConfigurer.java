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
package org.febit.boot.demo.doggy.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.auth.web.AuthHandlerInterceptor;
import org.febit.boot.auth.web.WebAuthHandler;
import org.febit.boot.demo.doggy.auth.AppAuth;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebAuthMvcConfigurer implements WebMvcConfigurer {

    private final WebAuthHandler<AppAuth> authHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(AuthHandlerInterceptor.create(authHandler))
                .addPathPatterns("/api/**");
    }
}
