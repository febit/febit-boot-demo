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
package org.febit.boot.demo.doggy.model;

import java.time.Instant;

public interface IChangeTracing {

    void setCreatedAt(Instant createdAt);

    void setUpdatedAt(Instant updatedAt);

    void setCreatedBy(String createdBy);

    void setUpdatedBy(String updatedBy);

    default void created(String code) {
        var now = Instant.now();
        setCreatedAt(now);
        setUpdatedAt(now);
        setCreatedBy(code);
        setUpdatedBy(code);
    }

    default void updated(String code) {
        setUpdatedAt(Instant.now());
        setUpdatedBy(code);
    }
}
