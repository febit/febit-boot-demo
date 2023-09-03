package org.febit.boot.demo.doggy.model.account;

import lombok.Data;
import org.febit.boot.common.model.IModel;
import org.febit.boot.demo.doggy.jmodel.po.AccountPermissionPO;

import java.time.Instant;

@Data
public class AccountPermissionCreateForm implements IModel<AccountPermissionPO> {

    private Long id;
    private Long accountId;
    private String code;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

}
