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
package org.febit.boot.demo.doggy.auth;

import lombok.experimental.UtilityClass;
import org.febit.boot.demo.doggy.auth.model.AppAuthImpl;

@UtilityClass
public class Auths {

    public static final AppAuth ANONYMOUS = AppAuthImpl.builder()
            .id(401L)
            .code("anonymous")
            .displayName("Anonymous")
            .build();

    public static final AppAuth SYSTEM = AppAuthImpl.builder()
            .id(101L)
            .code("system")
            .displayName("System")
            .build();

    public static final AppAuth ADMIN = AppAuthImpl.builder()
            .id(201L)
            .code("admin")
            .displayName("Admin")
            .build();

}
