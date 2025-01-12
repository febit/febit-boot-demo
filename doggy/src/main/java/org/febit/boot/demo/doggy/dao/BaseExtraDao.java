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
package org.febit.boot.demo.doggy.dao;

import org.febit.common.jooq.BaseCurdDao;
import org.febit.common.jooq.IEntity;
import org.febit.common.jooq.ITable;
import org.jooq.Configuration;
import org.jooq.UpdatableRecord;

public abstract class BaseExtraDao<TB extends ITable<R, ID>, PO extends IEntity<ID>, ID, R extends UpdatableRecord<R>>
        extends BaseCurdDao<TB, PO, ID, R> {

    protected BaseExtraDao(Configuration conf) {
        super(conf);
    }
}
