drop table if exists users;

drop table if exists roles;

drop table if exists users_roles;

create table if not exists users
(
    id         serial,
    username varchar(50),
    surname  varchar(50),
    age        int,
    password varchar(255),
    primary key (id)
);

create table if not exists roles(
                                    id serial,
                                    role varchar(50),
                                    primary key (id)
);

