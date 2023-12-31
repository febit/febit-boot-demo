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
package org.febit.boot.demo.doggy.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.febit.boot.demo.doggy.JsonApiMapping;
import org.febit.boot.demo.doggy.Permissions;
import org.febit.boot.demo.doggy.model.doggy.DoggyBreed;
import org.febit.boot.demo.doggy.model.doggy.DoggyBreedVO;
import org.febit.lang.protocol.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.febit.lang.util.Lists;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Doggy Breads")
@JsonApiMapping({
        "/api/v1/doggy-breeds"
})
@RequiredArgsConstructor
public class DoggyBreedApi implements IBasicApi {

    @GetMapping
    @Permissions.Basic
    public IResponse<List<DoggyBreedVO>> listAll() {
        return ok(Lists.collect(
                DoggyBreed.values(),
                DoggyBreedVO::of
        ));
    }
}
