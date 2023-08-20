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
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.demo.doggy.model.doggy.DoggyBreed;
import org.febit.boot.demo.doggy.model.doggy.DoggyBreedVO;
import org.febit.boot.web.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.febit.lang.util.Lists;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@Tag(name = "Doggy Breads")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {
        "/api/v1/doggy-breeds"
}, produces = {
        MediaType.APPLICATION_JSON_VALUE
})
public class DoggyBreedApi implements IBasicApi {

    @GetMapping
    public IResponse<List<DoggyBreedVO>> listAll() {
        return ok(Lists.collect(
                DoggyBreed.values(),
                DoggyBreedVO::of
        ));
    }
}
