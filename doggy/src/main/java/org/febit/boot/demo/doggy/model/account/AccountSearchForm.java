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
package org.febit.boot.demo.doggy.model.account;

import lombok.Data;
import org.febit.boot.demo.doggy.jmodel.table.TAccount;
import org.febit.boot.jooq.OrderMappingBy;
import org.febit.boot.jooq.SearchForm;

@Data
@OrderMappingBy(AccountOrderMapping.class)
public class AccountSearchForm implements SearchForm {

    @Keyword({
            TAccount.Columns.USERNAME,
            TAccount.Columns.DISPLAY_NAME,
    })
    private String q;

    @Equals(TAccount.Columns.ID)
    private Long id;

    @Equals(TAccount.Columns.USERNAME)
    private String username;

    @Equals(TAccount.Columns.DISPLAY_NAME)
    private String displayName;

    @Equals(TAccount.Columns.ENABLED)
    private Boolean enabled;

    @Equals(TAccount.Columns.CREATED_BY)
    private String createdBy;

    @Equals(TAccount.Columns.UPDATED_BY)
    private String updatedBy;

}
