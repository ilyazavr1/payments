truncate users cascade;
truncate roles cascade;
truncate accounts cascade;
truncate payments cascade;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS payments CASCADE;



create table roles
(
    id   serial      NOT NULL,
    name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO roles
values (default, 'ADMINISTRATOR');
INSERT INTO roles
values (default, 'CLIENT');

create table users
(
    id         serial       NOT NULL,
    first_name varchar(50)  NOT NULL,
    last_name  varchar(50)  NOT NULL,
    surname    varchar(50)  NOT NULL,
    email      varchar(50)  NOT NULL,
    password   varchar(255) NOT NULL,
    blocked    bool         NOT NULL DEFAULT FALSE,
    role_id    integer      REFERENCES roles (id) ON DELETE SET NULL,
    PRIMARY KEY (id)
);



create table accounts
(
    id      serial         NOT NULL,
    number  bigint         NOT NULL,
    money   numeric(10, 2) NOT NULL DEFAULT 0,
    user_id bigint         NOT NULL REFERENCES users (id),
    PRIMARY KEY (id)
);


create table payments
(
    id                 serial NOT NULL,
    money   numeric(10, 2) NOT NULL DEFAULT 0,
    sent               bool   NOT NULL DEFAULT FALSE,
    creation_timestamp timestamp       DEFAULT CURRENT_TIMESTAMP,
    user_id            bigint NOT NULL REFERENCES users (id),
    account_id         bigint NOT NULL REFERENCES accounts (id),
    PRIMARY KEY (id)
);

SET timezone = 'Europe/Kiev';