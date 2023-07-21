INSERT INTO users (username, password, enabled)
  values ('user',
    '$2a$10$oflh4XNSh9qh7CzlO94SBu6IZG1MZKbxGqzzuVPlwYDBdSIp/kwQC',
    1);

INSERT INTO authorities (username, authority)
  values ('user', 'ROLE_USER');