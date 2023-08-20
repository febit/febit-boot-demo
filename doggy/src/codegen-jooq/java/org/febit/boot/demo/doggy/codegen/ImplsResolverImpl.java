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
package org.febit.boot.demo.doggy.codegen;

import org.febit.boot.devkit.jooq.runtime.spi.ImplementsResolver;
import org.jooq.codegen.GeneratorStrategy;
import org.jooq.meta.TableDefinition;

public class ImplsResolverImpl implements ImplementsResolver {

    private static final String PKG_MODEL = "org.febit.boot.demo.doggy.model";

    @Override
    public void resolve(Context context) {
        if (!context.isTableDefinition()) {
            return;
        }
        if (context.getMode() != GeneratorStrategy.Mode.POJO) {
            return;
        }
        var table = (TableDefinition) context.getDef();
        namedModel(table, context);
        updateTraceModel(table, context);
    }

    private void namedModel(TableDefinition table, Context context) {
        if (!ColumnChecker.string(table, "name")) {
            return;
        }
        context.addImpl(PKG_MODEL + ".INamed");
    }

    private void updateTraceModel(TableDefinition table, Context context) {
        if (!ColumnChecker.dateTime(table, "created_at")
                || !ColumnChecker.dateTime(table, "updated_at")
                || !ColumnChecker.string(table, "created_by")
                || !ColumnChecker.string(table, "updated_by")
        ) {
            return;
        }
        context.addImpl(PKG_MODEL + ".IChangeTracing");
    }

}
