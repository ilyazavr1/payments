truncate "user" cascade;
truncate role cascade;
truncate card cascade;
truncate payment cascade;
truncate payment_status cascade;
truncate card_unblock_request cascade;
DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS payment CASCADE;
DROP TABLE IF EXISTS payment_status CASCADE;
DROP TABLE IF EXISTS card_unblock_request CASCADE;

/*truncate user_account cascade;
DROP TABLE IF EXISTS user_account CASCADE;
*/


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



create table card
(
    id                  serial                        NOT NULL,
    name                varchar(50)                   NOT NULL DEFAULT 'card',
    number              varchar(128)                  NOT NULL,
    money               integer                       NOT NULL DEFAULT 0,
    blocked             bool                          NOT NULL DEFAULT FALSE,
    under_consideration bool                          NOT NULL DEFAULT FALSE,
    user_id             bigint REFERENCES "user" (id) NOT NULL,
    PRIMARY KEY (id)
);

create table card_unblock_request
(
    id          serial                        NOT NULL,
    user_id     bigint REFERENCES "user" (id) NOT NULL,
    card_id     bigint REFERENCES card (id)   NOT NULL,
    first_name  varchar(50)                   NOT NULL,
    last_name   varchar(50)                   NOT NULL,
    surname     varchar(50)                   NOT NULL,
    card_number varchar(128)                  NOT NULL,
    money       integer                       NOT NULL DEFAULT 0,
    blocked     bool                          NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

create table payment
(
    id                  serial                        NOT NULL,
    balance             integer                       NOT NULL                                          DEFAULT 0,
    balance_destination integer                       NOT NULL                                          DEFAULT 0,
    money               integer                       NOT NULL                                          DEFAULT 0,
    payment_status_id   integer                       REFERENCES payment_status (id) ON DELETE SET NULL DEFAULT 1,
    creation_timestamp  timestamp                                                                       DEFAULT CURRENT_TIMESTAMP,
    card_sender_id      bigint                        NOT NULL REFERENCES card (id),
    card_destination_id bigint                        NOT NULL REFERENCES card (id),
    user_id             bigint REFERENCES "user" (id) NOT NULL,
    user_destination_id bigint REFERENCES "user" (id) NOT NULL,
    PRIMARY KEY (id)
);

/*SET timezone = 'Europe/Kiev';*/
SET TIME ZONE 'Europe/Kiev';



INSERT INTO "user"
VALUES (default, 'admin', 'admin', 'admin', 'admin@gmail.com',
        '747e2fea27df74d88affc130918788d2d2ca7f83b2e664129ebbdc48bf1ca9e29419e3523765a6b22bce8b2781236a2227e62196385328f9bc023df037a1ef807b7ae22254557000aaa79a19ad22834e',
        default, 1);

INSERT INTO "user"
VALUES (default, 'Влад', 'Лизогуб', 'Батьковтч', 'vlad@gmail.com',
        '747e2fea27df74d88affc130918788d2d2ca7f83b2e664129ebbdc48bf1ca9e29419e3523765a6b22bce8b2781236a2227e62196385328f9bc023df037a1ef807b7ae22254557000aaa79a19ad22834e',
        default, 2);

INSERT INTO "user"
VALUES (default, 'Artem', 'Krivenko', 'Батьковтч', 'artem@gmail.com',
        '747e2fea27df74d88affc130918788d2d2ca7f83b2e664129ebbdc48bf1ca9e29419e3523765a6b22bce8b2781236a2227e62196385328f9bc023df037a1ef807b7ae22254557000aaa79a19ad22834e',
        default, 2);


/*create table user_account
(
    user_id    bigint REFERENCES "user" (id)  NOT NULL,
    account_id bigint REFERENCES account (id) NOT NULL NOT NULL,
    CONSTRAINT user_account_pkey PRIMARY KEY (user_id, account_id)
);
*/