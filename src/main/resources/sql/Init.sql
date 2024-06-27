INSERT INTO roles (role)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');

insert into users (id, name, surname, age, password)
values (id, 'admin', 'admin', 35, '100'),
       (id, 'user', 'user', 33, '100');

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 2);