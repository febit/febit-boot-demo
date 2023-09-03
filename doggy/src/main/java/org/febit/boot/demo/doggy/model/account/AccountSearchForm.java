package org.febit.boot.demo.doggy.model.account;

import lombok.Data;
import org.febit.boot.demo.doggy.jmodel.table.TAccount;
import org.febit.boot.jooq.OrderMappingBy;
import org.febit.boot.jooq.SearchForm;

@Data
@OrderMappingBy(AccountOrderMapping.class)
public class AccountSearchForm implements SearchForm {

    @Keyword({
            TAccount.Columns.USERNAME,
            TAccount.Columns.DISPLAY_NAME,
    })
    private String q;

    @Equals(TAccount.Columns.ID)
    private Long id;

    @Equals(TAccount.Columns.USERNAME)
    private String username;

    @Equals(TAccount.Columns.DISPLAY_NAME)
    private String displayName;

    @Equals(TAccount.Columns.ENABLED)
    private Boolean enabled;

    @Equals(TAccount.Columns.CREATED_BY)
    private String createdBy;

    @Equals(TAccount.Columns.UPDATED_BY)
    private String updatedBy;

}
