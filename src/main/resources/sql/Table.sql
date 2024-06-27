
DROP TABLE IF EXISTS users_roles;


DROP TABLE IF EXISTS roles;


DROP TABLE IF EXISTS users;


CREATE TABLE IF NOT EXISTS users
(
    id serial PRIMARY KEY,
    username VARCHAR(50),
    surname VARCHAR(50),
    age INT,
    password VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS roles
(
    id serial PRIMARY KEY,
    role VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS users_roles (
                                           user_id INT,
                                           role_id INT,
                                           PRIMARY KEY (user_id, role_id),
                                           FOREIGN KEY (user_id) REFERENCES users (id),
                                           FOREIGN KEY (role_id) REFERENCES roles (id)
);
