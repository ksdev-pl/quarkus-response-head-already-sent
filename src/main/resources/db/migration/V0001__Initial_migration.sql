create extension if not exists "uuid-ossp";

create table app_user
(
    id                bigint                   not null
        constraint pk_app_user_id
        primary key,
    uuid              uuid                     not null,
    version           integer,
    creation_date     timestamp with time zone not null,
    modification_date timestamp with time zone not null,
    username          varchar(1000)            not null
        constraint uk_app_user_username
        unique,
    password          varchar(1000)            not null
);

create sequence app_user_seq increment by 50;

create table role
(
    id                bigint                   not null
        constraint pk_role_id
        primary key,
    uuid              uuid                     not null,
    version           integer,
    creation_date     timestamp with time zone not null,
    modification_date timestamp with time zone not null,
    name              varchar(1000)            not null
        constraint uk_role_name
        unique
);

create sequence role_seq increment by 50;

create table app_user_role
(
    user_id  bigint not null
        constraint fk_app_user_role_role_user_id
            references app_user,
    role_id bigint not null
        constraint fk_app_user_role_role_id
            references role,
    constraint pk_app_user_role_id
    primary key (role_id, user_id)
);

insert into role (id, uuid, version, creation_date, modification_date, name) values
    (nextval('role_seq'), uuid_generate_v4(), 0, current_timestamp, current_timestamp, 'admin'),
    (nextval('role_seq'), uuid_generate_v4(), 0, current_timestamp, current_timestamp, 'user');