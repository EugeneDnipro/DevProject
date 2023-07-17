INSERT INTO users (username, password, enabled)
  values ('user',
    '$2a$10$cALnBqt6EW28yEcqThUrReBzKr3ijkXY4ouln.AquiIycVlHZqCIe',
    1);

INSERT INTO authorities (username, authority)
  values ('user', 'ROLE_USER');