DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS accounts;

create table roles
(
    id   serial      NOT NULL,
    name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO roles values (default, 'manager');
INSERT INTO roles values (default, 'user');

create table users
(
    id         serial       NOT NULL,
    first_name varchar(50)  NOT NULL,
    last_name  varchar(50)  NOT NULL,
    surname    varchar(50)  NOT NULL,
    email      varchar(50)  NOT NULL,
    password   varchar(255) NOT NULL,
    blocked    bool         NOT NULL DEFAULT FALSE,
    role_id   integer      references roles (id) ON DELETE SET NULL,
    PRIMARY KEY (id)
);



create table accounts
(
    id     serial  NOT NULL,
    number bigint  NOT NULL,
    PRIMARY KEY (id)
);

