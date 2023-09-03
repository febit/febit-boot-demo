SET search_path TO "doggy";

create table account
(
  id            bigserial,
  username      varchar(64)                              not null,
  display_name  varchar(64)                              not null,
  password_hash varchar(255)                             null,
  enabled       boolean     default true                 not null,
  created_at    timestamptz default CURRENT_TIMESTAMP(3) not null,
  updated_at    timestamptz default CURRENT_TIMESTAMP(3) not null,
  created_by    varchar(128),
  updated_by    varchar(128),
  constraint pk_account primary key (id),
  constraint uk_account_username unique (username)
);

SELECT setval('account_id_seq', 1000);

create table account_permission
(
  id         bigserial,
  account_id bigint                                   not null,
  code       varchar(64)                              not null,
  created_at timestamptz default CURRENT_TIMESTAMP(3) not null,
  updated_at timestamptz default CURRENT_TIMESTAMP(3) not null,
  created_by varchar(128),
  updated_by varchar(128),
  constraint pk_account_permission primary key (id),
  constraint uk_account_permission_account_id_code unique (account_id, code),
  constraint fk_account_permission_account_id
    foreign key (account_id) references account (id)
      on update cascade on delete restrict deferrable
);

SELECT setval('account_permission_id_seq', 20000);
