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
package org.febit.boot.demo.doggy.auth.model.account;

import lombok.Data;
import org.febit.boot.demo.doggy.jmodel.po.AccountPO;
import org.febit.boot.util.Models;

import java.time.Instant;

@Data
public class AccountVO {

    private Long id;
    private String username;
    private String displayName;
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    public static AccountVO of(AccountPO entity) {
        return Models.map(entity, AccountVO::new);
    }
}
