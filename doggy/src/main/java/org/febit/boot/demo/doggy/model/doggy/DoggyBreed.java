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
package org.febit.boot.demo.doggy.model.doggy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.febit.lang.Valued;

@Getter
@RequiredArgsConstructor
public enum DoggyBreed implements Valued<Integer> {

    UNKNOWN(0, "Unknown", "未知"),

    CHINESE_RURAL(101, "Chinese Rural", "中华田园"),
    ALASKAN(201, "Alaskan Malamute", "阿拉斯加"),
    HUSKY(301, "Siberian Husky", "哈士奇"),
    ;

    private final Integer value;
    private final String label;
    private final String labelZh;

    public String getId() {
        return name();
    }
}
