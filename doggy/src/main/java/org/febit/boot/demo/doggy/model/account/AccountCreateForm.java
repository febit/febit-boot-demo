package org.febit.boot.demo.doggy.model.account;

import lombok.Data;
import org.febit.boot.common.model.IModel;
import org.febit.boot.demo.doggy.jmodel.po.AccountPO;

@Data
public class AccountCreateForm implements IModel<AccountPO> {

    private String username;
    private String displayName;
    private Boolean enabled;
}
