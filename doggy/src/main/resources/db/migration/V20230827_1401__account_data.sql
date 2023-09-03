SET search_path TO "doggy";

INSERT INTO account(id, username, display_name, password_hash, enabled, created_at, updated_at, created_by, updated_by)
VALUES (101, 'system', 'System', null, false, '2023-08-27 00:00:00 +00:00', '2023-08-27 00:00:00 +00:00', 'system', 'system'),
       (201, 'admin', 'Admin', '$2y$05$S3HiQDVpBe2fm8aQWSeWheY2k9jbVSOeW8GXOV5o0088UW6tLHO0y', true, '2023-08-27 00:00:00 +00:00', '2023-08-27 00:00:00 +00:00', 'system', 'system'),
       (401, 'anonymous', 'Anonymous', null, true, '2023-08-27 00:00:00 +00:00', '2023-08-27 00:00:00 +00:00', 'system', 'system')
;

INSERT INTO account_permission (id, account_id, code, created_at, updated_at, created_by, updated_by)
VALUES (2001, 201, 'demo:all:admin', '2023-08-27 00:00:00 +00:00', '2023-08-27 00:00:00 +00:00', 'system', 'system')
;
