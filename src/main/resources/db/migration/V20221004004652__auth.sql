create table authentication
(
    id            bigint primary key auto_increment,
    provider_type varchar(255) not null,
    principal     varchar(255) not null,
    member_id     bigint       not null,
    created_date  datetime(6) not null
);

create unique index uix_authentication_provider on authentication (provider_type, principal);

create table refresh_token
(
    id           varchar(255) primary key,
    access_token varchar(255) not null,
    expires_in   int          not null,
    created_date datetime(6) not null
);

create table register_token
(
    id           varchar(255) primary key,
    body         json not null,
    expires_in   int  not null,
    created_date datetime(6) not null
);

create table member
(
    id           bigint primary key auto_increment,
    nickname     varchar(255) not null unique,
    email        varchar(255) not null,
    state        varchar(255) not null,
    city         varchar(255) not null,
    latitude     double       not null,
    longitude    double       not null,
    created_date datetime(6) not null
);

create table topic
(
    id           bigint primary key auto_increment,
    name         varchar(255) not null unique,
    created_date datetime(6) not null
);

create table personality
(
    id           bigint primary key auto_increment,
    name         varchar(255) not null unique,
    created_date datetime(6) not null
);

create table member_topic
(
    id        bigint primary key auto_increment,
    member_id bigint not null,
    topic_id  bigint not null
);

create table member_personality
(
    id             bigint primary key auto_increment,
    member_id      bigint not null,
    personality_id bigint not null
);

