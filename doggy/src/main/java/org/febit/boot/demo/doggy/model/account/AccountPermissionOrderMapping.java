package org.febit.boot.demo.doggy.model.account;

import lombok.Getter;

import java.time.Instant;

@Getter
public class AccountPermissionOrderMapping {

    private Long id;
    private Long accountId;
    private String code;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

}
