package org.febit.boot.demo.doggy.model.account;

import lombok.Data;
import org.febit.boot.common.util.Models;
import org.febit.boot.demo.doggy.jmodel.po.AccountPO;

import java.time.Instant;

@Data
public class AccountVO {

    private Long id;
    private String username;
    private String displayName;
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    public static AccountVO of(AccountPO entity) {
        return Models.map(entity, AccountVO::new);
    }
}
