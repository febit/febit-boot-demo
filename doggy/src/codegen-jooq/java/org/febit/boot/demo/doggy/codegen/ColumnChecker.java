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

import org.jooq.meta.TableDefinition;

import java.util.regex.Pattern;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class ColumnChecker {

    static final Pattern PATTERN_INT = Pattern.compile("(?i)int|integer");
    static final Pattern PATTERN_STRING = Pattern.compile(
            "(?i)varchar|char|string|varchar_ignorecase|character|character varying");
    static final Pattern PATTERN_DATETIME = Pattern.compile(
            "(?i)datetime|timestamp|timestamptz|timestamp with time zone");

    static boolean check(TableDefinition table, String name, Pattern typePattern) {
        var col = table.getColumn(name, true);
        if (col == null) {
            return false;
        }
        return typePattern.matcher(col.getType().getType())
                .matches();
    }

    static boolean integer(TableDefinition table, String name) {
        return check(table, name, PATTERN_INT);
    }

    static boolean dateTime(TableDefinition table, String name) {
        return check(table, name, PATTERN_DATETIME);
    }

    static boolean string(TableDefinition table, String name) {
        return check(table, name, PATTERN_STRING);
    }
}
