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

import org.febit.boot.common.permission.PermissionItem;
import org.febit.boot.common.permission.PermissionVerifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DemoPermissionVerifierImpl implements PermissionVerifier<DemoAuth> {

    public static final String ADMIN = "admin";
    public static final String ANONYMOUS = "anonymous";

    @Override
    public boolean isAllow(DemoAuth auth, Collection<PermissionItem> allows) {
        var code = auth.getCode().toLowerCase();
        if (ADMIN.equals(code)) {
            return true;
        }
        if (ANONYMOUS.equals(code)) {
            return false;
        }
        // Basic roles for basic accounts.
        return allows.stream()
                .map(PermissionItem::getCode)
                .anyMatch(p -> p.endsWith(":basic"));
    }
}
