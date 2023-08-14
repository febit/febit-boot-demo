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

import org.febit.boot.demo.doggy.jmodel.po.DoggyPO;
import org.febit.boot.demo.doggy.jmodel.record.DoggyRecord;
import org.febit.boot.demo.doggy.jmodel.table.TDoggy;
import org.febit.boot.jooq.BaseCurdDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class DoggyDao extends BaseCurdDao<TDoggy, DoggyPO, Integer, DoggyRecord> {

    public DoggyDao(Configuration conf) {
        super(conf);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isNameUsed(String name) {
        return existsBy(
                T.NAME.eq(name)
        );
    }
}
