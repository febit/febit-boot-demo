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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.febit.boot.demo.doggy.JsonApiMapping;
import org.febit.boot.demo.doggy.Permissions;
import org.febit.boot.demo.doggy.model.doggy.DoggyCreateForm;
import org.febit.boot.demo.doggy.model.doggy.DoggySearchForm;
import org.febit.boot.demo.doggy.model.doggy.DoggyUpdateForm;
import org.febit.boot.demo.doggy.model.doggy.DoggyVO;
import org.febit.boot.demo.doggy.service.DoggyCurd;
import org.febit.boot.util.Errors;
import org.febit.lang.protocol.IBasicApi;
import org.febit.lang.protocol.IResponse;
import org.febit.lang.protocol.Page;
import org.febit.lang.protocol.Pagination;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Tag(name = "Doggies API")
@RestController
@RequiredArgsConstructor
@JsonApiMapping({
        "/api/v1/doggies"
})
public class DoggyApi implements IBasicApi {

    final DoggyCurd curd;

    @Permissions.Basic
    @GetMapping("/{id}")
    public IResponse<DoggyVO> requireById(
            @PathVariable Integer id
    ) {
        return ok(
                curd.requireVoById(id)
        );
    }

    @Permissions.Basic
    @PostMapping("/list")
    public IResponse<Collection<DoggyVO>> list(
            @RequestBody @Valid DoggySearchForm form
    ) {
        return ok(
                curd.list(form)
        );
    }

    @Permissions.Basic
    @PostMapping("/search")
    public IResponse<Page<DoggyVO>> search(
            Pagination page,
            @RequestBody @Valid DoggySearchForm form
    ) {
        return ok(
                curd.search(form, page)
        );
    }

    @Permissions.Admin
    @PostMapping
    public IResponse<DoggyVO> create(
            @RequestBody @Valid DoggyCreateForm form
    ) {
        return ok(
                curd.create(form)
        );
    }

    @Permissions.Admin
    @PutMapping("/{id}")
    public IResponse<DoggyVO> update(
            @PathVariable Integer id,
            @RequestBody @Valid DoggyUpdateForm form
    ) {
        return ok(curd.update(id, form));
    }

    @Permissions.Admin
    @DeleteMapping("/{id}")
    public IResponse<Void> deleteById(
            @PathVariable Integer id
    ) {
        curd.deleteById(id);
        return ok();
    }

    @Permissions.Admin
    @DeleteMapping("/by-ids/{ids}")
    public IResponse<Void> deleteByIds(
            @PathVariable List<Integer> ids
    ) {
        Errors.ILLEGAL_ARG.whenFalse(ids.size() <= 1000,
                "args.size.less-equals-than", "ids", 100);
        curd.deleteByIds(ids);
        return ok();
    }

}
