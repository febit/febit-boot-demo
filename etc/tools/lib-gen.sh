#!/usr/bin/env bash

[ -n "$BASE_PKG" ] || echo "required env 'BASE_PKG'" && false
[ -n "$MODULE_DIR" ] || echo "required env 'MODULE_DIR'" && false

JMODEL_DIR="$MODULE_DIR/build/generated/sources/codegen-jooq"
EXAMPLES_DIR="$MODULE_DIR/build/examples"

MODEL_DIR="$EXAMPLES_DIR/model"
API_DIR="$EXAMPLES_DIR/api"
DAO_DIR="$EXAMPLES_DIR/dao"
CRUD_DIR="$EXAMPLES_DIR/service"

JMODEL_PKG="$BASE_PKG.jmodel"
MODEL_PKG="$BASE_PKG.model"
API_PKG="$BASE_PKG.api"
DAO_PKG="$BASE_PKG.dao"
CRUD_PKG="$BASE_PKG.service"

echo "Generated examples to dir: ${EXAMPLES_DIR}"
mkdir -p "$MODEL_DIR" "$API_DIR" "$DAO_DIR" "$CRUD_DIR"

function extract_entity_id_type(){
  local file=$1
  local entity=$2
  local id
  while read -r line; do
    id="$(echo "$line" | grep -Po "(?<=class ${entity}PO implements IEntity<)(.+?)(?=[>][, ])")"
    [ -n "$id" ] && echo "$id" && return 0
  done < "$file"
}

function extract_field_lines(){
  local file=$1
  while read -r line; do
    id="$(echo "$line" \
        | grep -Po 'private ([A-Z_][a-zA-Z0-9_,<> ]*)([ ]+)([a-zA-Z0-9_]+);' \
        | awk '{print "    "$0}'
    )"
    [ -n "$id" ] && echo "$id"
  done < "$file"
}

function to_search_field_lines(){
  echo "$1" | while read -r line; do
    field="$(echo "$line" | grep -Po "([a-zA-Z0-9_]+)(?=;)")"
    field_underscore="$(sed -e 's/\([A-Z]\)/_\1/g' <<< "${field}")"
    echo ""
    echo "    @Equals(T${entity}.Columns.${field_underscore^^})"
    echo "    $line"
  done
}

function extract_imports_lines(){
  local file=$1
  while read -r line; do
    id="$(echo "$line" \
        | grep -Po 'import (.*);' \
        | grep -v 'import jakarta.persistence.' \
        | grep -v 'import org.febit.boot.' \
        | grep -v "${JMODEL_PKG}.record."
    )"
    [ -n "$id" ] && echo "$id"
  done < "$file"
}

for f in "$JMODEL_DIR/${JMODEL_PKG//.//}/po/"*.java; do
  entity="$(basename "$f" PO.java)"
  entity_camel_lower="${entity,}"
  entity_bar="$(sed -e 's/\([A-Z]\)/-\L\1/g' <<< "${entity_camel_lower}")"
  entity_display="$(sed -e 's/\([A-Z]\)/ \1/g' <<< "${entity_camel_lower}")"
  entity_display="${entity_display^}"
  entity_id_type="$(extract_entity_id_type "$f" "$entity")"
  imports_lines="$(extract_imports_lines "$f")"
  all_field_lines="$(extract_field_lines "$f")"
  form_field_lines="$(echo "$all_field_lines" | grep -v ' (id|createdAt|createdBy|updatedAt|updatedBy);')"
  search_field_lines="$(to_search_field_lines "$all_field_lines")"

# ======================================= #
  cat > "${MODEL_DIR}/${entity}VO.java" <<EOF
package ${MODEL_PKG};

import ${JMODEL_PKG}.po.${entity}PO;
import lombok.Data;
import org.febit.boot.util.Models;

${imports_lines}

@Data
public class ${entity}VO {

${all_field_lines}

    public static ${entity}VO of(${entity}PO entity) {
        return Models.map(entity, ${entity}VO::new);
    }
}
EOF

# ======================================= #
  cat > "${MODEL_DIR}/${entity}BriefVO.java" <<EOF
package ${MODEL_PKG};

import ${JMODEL_PKG}.po.${entity}PO;
import lombok.Data;
import org.febit.boot.util.Models;

${imports_lines}

@Data
public class ${entity}BriefVO {

${all_field_lines}

    public static ${entity}BriefVO of(${entity}PO entity) {
        return Models.map(entity, ${entity}BriefVO::new);
    }
}
EOF

# ======================================= #
  cat > "${MODEL_DIR}/${entity}CreateForm.java" <<EOF
package ${MODEL_PKG};

import ${JMODEL_PKG}.po.${entity}PO;
import lombok.Data;
import org.febit.boot.model.IModel;

${imports_lines}

@Data
public class ${entity}CreateForm implements IModel<${entity}PO> {

${form_field_lines}

}
EOF

# ======================================= #
  cat > "${MODEL_DIR}/${entity}UpdateForm.java" <<EOF
package ${MODEL_PKG};

import org.febit.boot.model.IModel;
import ${JMODEL_PKG}.po.${entity}PO;
import lombok.Data;

${imports_lines}

@Data
public class ${entity}UpdateForm implements IModel<${entity}PO> {

${form_field_lines}

}
EOF

# ======================================= #
  cat > "${MODEL_DIR}/${entity}OrderMapping.java" <<EOF
package ${MODEL_PKG};

import lombok.Getter;

${imports_lines}

@Getter
public class ${entity}OrderMapping {

${all_field_lines}

}
EOF

# ======================================= #

  cat > "${MODEL_DIR}/${entity}SearchForm.java" <<EOF
package ${MODEL_PKG};

import ${JMODEL_PKG}.table.T${entity};
import lombok.Data;
import org.febit.common.jooq.OrderMappingBy;
import org.febit.common.jooq.SearchForm;

${imports_lines}

@Data
@OrderMappingBy(${entity}OrderMapping.class)
public class ${entity}SearchForm implements SearchForm {

    @Keyword({
    })
    private String q;

${search_field_lines}

}
EOF

# ======================================= #
  cat > "${DAO_DIR}/${entity}Dao.java" <<EOF
package ${DAO_PKG};

import ${JMODEL_PKG}.po.${entity}PO;
import ${JMODEL_PKG}.record.${entity}Record;
import ${JMODEL_PKG}.table.T${entity};
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class ${entity}Dao extends BaseExtraDao<T${entity}, ${entity}PO, ${entity_id_type}, ${entity}Record> {

    public ${entity}Dao(Configuration conf) {
        super(conf);
    }
}
EOF

# ======================================= #
  cat > "${API_DIR}/${entity}Api.java" <<EOF
package ${API_PKG};

import ${MODEL_PKG}.${entity}BriefVO;
import ${MODEL_PKG}.${entity}CreateForm;
import ${MODEL_PKG}.${entity}UpdateForm;
import ${MODEL_PKG}.${entity}SearchForm;
import ${MODEL_PKG}.${entity}VO;
import ${CRUD_PKG}.${entity}Curd;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.util.Errors;
import org.febit.lang.protocol.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "${entity_display}s")
@RequestMapping(value = {
        "/api/v1/${entity_bar}s"
}, produces = {
         MediaType.APPLICATION_JSON_VALUE
})
public class ${entity}Api implements IBasicApi {

    private final ${entity}Curd curd;

    @GetMapping("/{id}")
    public IResponse<${entity}VO> requireById(
            @PathVariable ${entity_id_type} id
    ) {
        return ok(${entity}VO.of(
                curd.require(id)
        ));
    }

    @PostMapping("/list")
    public IResponse<List<${entity}BriefVO>> list(
            @RequestBody @Valid ${entity}SearchForm form
    ) {
        return ok(curd.list(form));
    }

    @PostMapping("/search")
    public IResponse<Page<${entity}BriefVO>> search(
            Pagination page,
            @RequestBody @Valid ${entity}SearchForm form
    ) {
        return ok(curd.search(page, form));
    }

    @PostMapping("")
    public IResponse<${entity}VO> create(
            @RequestBody @Valid ${entity}CreateForm form
    ) {
        return ok(${entity}VO.of(
                curd.create(form)
        ));
    }

    @PutMapping("/{id}")
    public IResponse<${entity}VO> update(
            @PathVariable ${entity_id_type} id,
            @RequestBody @Valid ${entity}UpdateForm form
    ) {
        return ok(${entity}VO.of(
                curd.update(id, form)
        ));
    }

    @DeleteMapping("/{id}")
    public IResponse<Void> deleteById(
            @PathVariable ${entity_id_type} id
    ) {
        curd.deleteById(id);
        return ok();
    }

    @DeleteMapping("/by-ids/{ids}")
    public IResponse<Void> deleteByIds(
            @PathVariable List<${entity_id_type}> ids
    ) {
        curd.deleteByIds(ids);
        return ok();
    }
}
EOF

# ======================================= #
  cat > "${CRUD_DIR}/${entity}Curd.java" <<EOF
package ${CRUD_PKG};

import ${DAO_PKG}.${entity}Dao;
import ${JMODEL_PKG}.po.${entity}PO;
import ${MODEL_PKG}.${entity}BriefVO;
import ${MODEL_PKG}.${entity}CreateForm;
import ${MODEL_PKG}.${entity}UpdateForm;
import ${MODEL_PKG}.${entity}SearchForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.febit.boot.util.Errors;
import org.febit.lang.protocol.Page;
import org.febit.lang.protocol.Pagination;
import org.febit.lang.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ${entity}Curd {

    private final AuthSubject auth;

    private final ${entity}Dao dao;

    public ${entity}PO require(${entity_id_type} id) {
        var entity = dao.findById(id);
        Errors.NOT_FOUND.whenNull(entity, "Not found ${entity_bar}: {0}", id);
        assert entity != null;
        return entity;
    }

    public Page<${entity}BriefVO> search(Pagination page, ${entity}SearchForm form) {
        return dao.page(page, form)
                .transfer(${entity}BriefVO::of);
    }

    public List<${entity}BriefVO> list(${entity}SearchForm form) {
        return Lists.collect(
                dao.listBy(form),
                ${entity}BriefVO::of
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public ${entity}PO create(${entity}CreateForm form) {
        var entity = form.to(${entity}PO::new);
        entity.created(auth);
        dao.insert(entity);
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    public ${entity}PO update(${entity_id_type} id, ${entity}UpdateForm form) {
        var entity = require(id);
        form.to(entity);
        entity.updated(auth);
        dao.update(entity);
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(${entity_id_type} id) {
        dao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<${entity_id_type}> ids) {
        dao.deleteByIds(ids);
    }
}
EOF

done
