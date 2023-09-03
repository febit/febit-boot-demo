CREATE SCHEMA IF NOT EXISTS "doggy";
SET search_path TO "doggy";

DROP TABLE IF EXISTS t_doggy;
CREATE TABLE t_doggy
(
  id          serial primary key,
  gender      int2                     default 0                 not null,
  breed       int                      default 0                 not null,
  name        varchar(128)                                       not null,
  created_at  timestamp with time zone default CURRENT_TIMESTAMP not null,
  updated_at  timestamp with time zone default CURRENT_TIMESTAMP not null,
  created_by  varchar(128)                                       null,
  updated_by  varchar(128)                                       null,
  description varchar(255)                                       null,
  constraint uk_doggy_name unique (name)
);
