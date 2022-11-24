create table report
(
    id           bigint primary key auto_increment,
    member_id    bigint       not null,
    letter_id    bigint       not null,
    description  varchar(100) not null,
    completed    tinyint      not null,
    created_date datetime(6) not null
);

