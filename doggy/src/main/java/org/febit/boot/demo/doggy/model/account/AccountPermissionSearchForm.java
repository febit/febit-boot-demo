package org.febit.boot.demo.doggy.model.account;

import lombok.Data;
import org.febit.boot.demo.doggy.jmodel.table.TAccountPermission;
import org.febit.boot.jooq.OrderMappingBy;
import org.febit.boot.jooq.SearchForm;

@Data
@OrderMappingBy(AccountPermissionOrderMapping.class)
public class AccountPermissionSearchForm implements SearchForm {

    @Keyword({
            TAccountPermission.Columns.CODE,
    })
    private String q;

    @Equals(TAccountPermission.Columns.ID)
    private Long id;

    @Equals(TAccountPermission.Columns.ACCOUNT_ID)
    private Long accountId;

    @Equals(TAccountPermission.Columns.CODE)
    private String code;

    @Equals(TAccountPermission.Columns.CREATED_BY)
    private String createdBy;

    @Equals(TAccountPermission.Columns.UPDATED_BY)
    private String updatedBy;

}
