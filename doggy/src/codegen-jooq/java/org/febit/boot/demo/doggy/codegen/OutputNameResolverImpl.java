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

import org.febit.boot.common.util.Priority;
import org.febit.boot.devkit.jooq.runtime.spi.OutputNameResolver;
import org.jooq.meta.Definition;
import org.springframework.core.annotation.Order;

@Order(Priority.HIGH)
public class OutputNameResolverImpl implements OutputNameResolver {

    private static String removeTablePrefix(String name) {
        return name.replaceAll("^(?i)t_", "");
    }

    @Override
    public String resolve(Definition definition) {
        return removeTablePrefix(
                definition.getOutputName()
        );
    }
}
