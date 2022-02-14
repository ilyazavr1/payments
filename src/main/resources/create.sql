truncate "user" cascade;
truncate role cascade;
truncate account cascade;
truncate payment cascade;
truncate user_account cascade;
DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS payment CASCADE;
DROP TABLE IF EXISTS user_account CASCADE;



create table role
(
    id   serial      NOT NULL,
    name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

create table payment_status
(
    id     serial      NOT NULL,
    status varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO role
values (default, 'ADMINISTRATOR');
INSERT INTO role
values (default, 'CLIENT');

INSERT INTO payment_status
values (default, 'PREPARED');
INSERT INTO payment_status
values (default, 'SENT');
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
    id      serial                        NOT NULL,
    number  varchar(128)                  NOT NULL,
    money   integer                       NOT NULL DEFAULT 0,
    user_id bigint REFERENCES "user" (id) NOT NULL,
    PRIMARY KEY (id)
);


create table payment
(
    id                     serial  NOT NULL,
    money                  integer NOT NULL DEFAULT 0,
    payment_status_id      integer REFERENCES payment_status (id) ON DELETE SET NULL,
    creation_timestamp     timestamp        DEFAULT CURRENT_TIMESTAMP,
    account_sender_id      bigint  NOT NULL REFERENCES account (id),
    account_destination_id bigint  NOT NULL REFERENCES account (id),
    PRIMARY KEY (id)
);


SET timezone = 'Europe/Kiev';



create table user_account
(
    user_id    bigint REFERENCES "user" (id)  NOT NULL,
    account_id bigint REFERENCES account (id) NOT NULL NOT NULL,
    CONSTRAINT user_account_pkey PRIMARY KEY (user_id, account_id)
);
