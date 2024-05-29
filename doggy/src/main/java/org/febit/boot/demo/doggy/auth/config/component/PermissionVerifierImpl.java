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
package org.febit.boot.demo.doggy.auth.config.component;

import lombok.RequiredArgsConstructor;
import org.febit.boot.common.permission.PermissionItem;
import org.febit.boot.common.permission.PermissionVerifier;
import org.febit.boot.demo.doggy.Permissions;
import org.febit.boot.demo.doggy.auth.Auths;
import org.febit.boot.demo.doggy.auth.AppAuth;
import org.febit.boot.demo.doggy.auth.service.AuthService;
import org.febit.lang.util.Lists;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class PermissionVerifierImpl implements PermissionVerifier<AppAuth> {

    public static final String ADMIN = Auths.ADMIN.getCode();
    public static final String ANONYMOUS = Auths.ANONYMOUS.getCode();

    private static final String BASIC_OF_ALL = Permissions.MODULE
            + ':' + Permissions.ALL + ':' + Permissions.BASIC;

    private final AuthService authService;

    @Override
    public boolean isAllow(AppAuth auth, Collection<PermissionItem> allows) {
        var code = auth.getCode().toLowerCase();
        if (ADMIN.equals(code)) {
            return true;
        }
        if (ANONYMOUS.equals(code)) {
            return false;
        }

        // Basic roles for basic accounts.
        var allowBasic = allows.stream()
                .map(PermissionItem::getCode)
                .anyMatch(p -> p.equals(BASIC_OF_ALL));
        if (allowBasic) {
            return true;
        }

        return authService.checkPermissions(auth,
                Lists.collect(allows, PermissionItem::getCode)
        );
    }
}
