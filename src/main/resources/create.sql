truncate "user" cascade;
truncate role cascade;
truncate account cascade;
truncate payment cascade;
DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS payment CASCADE;



create table role
(
    id   serial      NOT NULL,
    name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO role
values (default, 'ADMINISTRATOR');
INSERT INTO role
values (default, 'CLIENT');

create table "user"
(
    id         serial       NOT NULL,
    first_name varchar(50)  NOT NULL,
    last_name  varchar(50)  NOT NULL,
    surname    varchar(50)  NOT NULL,
    email      varchar(50)  NOT NULL,
    password   varchar(512) NOT NULL,
    blocked    bool         NOT NULL DEFAULT FALSE,
    role_id    integer      REFERENCES role (id) ON DELETE SET NULL,
    PRIMARY KEY (id)
);



create table account
(
    id     serial         NOT NULL,
    number varchar(128)   NOT NULL,
    money  numeric(10, 2) NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);


create table payment
(
    id                 serial         NOT NULL,
    money              numeric(10, 2) NOT NULL DEFAULT 0,
    sent               bool           NOT NULL DEFAULT FALSE,
    creation_timestamp timestamp               DEFAULT CURRENT_TIMESTAMP,
    user_id            bigint         NOT NULL REFERENCES "user" (id),
    account_id         bigint         NOT NULL REFERENCES account (id),
    PRIMARY KEY (id)
);
create table user_account
(
    user_id    bigint REFERENCES "user" (id)  NOT NULL,
    account_id bigint REFERENCES account (id) NOT NULL NOT NULL
);
SET timezone = 'Europe/Kiev';