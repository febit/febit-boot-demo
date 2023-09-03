package org.febit.boot.demo.doggy.model.account;

import lombok.Data;
import org.febit.boot.common.model.IModel;
import org.febit.boot.demo.doggy.jmodel.po.AccountPermissionPO;

@Data
public class AccountPermissionUpdateForm implements IModel<AccountPermissionPO> {

    private Long accountId;
    private String code;
}
