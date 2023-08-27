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

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.febit.boot.demo.doggy.DoggyApiVersion;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SpringdocConfig {

    private static final String AUTH_FAKE = "Fake-Token";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(AUTH_FAKE,
                                new SecurityScheme()
                                        .name(HttpHeaders.AUTHORIZATION)
                                        .in(SecurityScheme.In.HEADER)
                                        .type(SecurityScheme.Type.APIKEY)
                        )
                )
                .info(new Info()
                        .title("Demo API")
                        .version("v" + DoggyApiVersion.version()
                                + "-" + DoggyApiVersion.builtAt()
                        )
                );
    }

    @Bean
    public GroupedOpenApi apiGroup_v1() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/v1/**")
                .addOperationCustomizer(this::applyFakeAuth)
                .build();
    }

    private Operation applyFakeAuth(Operation operation, HandlerMethod handlerMethod) {
        return operation.addSecurityItem(
                new SecurityRequirement().addList(AUTH_FAKE)
        );
    }
}
