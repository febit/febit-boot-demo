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
package org.febit.boot.demo.doggy.config;

import org.febit.boot.common.auth.AuthSuppliers;
import org.febit.boot.common.auth.ThreadLocalAuthSupplier;
import org.febit.boot.demo.doggy.model.auth.DelegatedDemoAuth;
import org.febit.boot.demo.doggy.model.auth.DemoAuth;
import org.febit.boot.web.util.RequestAttributeAuthSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AuthConfig {

    @Bean
    public DemoAuth demoAuth() {
        var resolvers = List.of(
                new ThreadLocalAuthSupplier<DemoAuth>(),
                RequestAttributeAuthSupplier.create(DemoAuth.class)
        );
        return DelegatedDemoAuth.delegated(
                () -> AuthSuppliers.get(resolvers)
        );
    }
}
