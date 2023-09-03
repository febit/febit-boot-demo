package org.febit.boot.demo.doggy.model.account;

import lombok.Getter;

import java.time.Instant;

@Getter
public class AccountOrderMapping {

    private Long id;
    private String username;
    private String displayName;
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

}
