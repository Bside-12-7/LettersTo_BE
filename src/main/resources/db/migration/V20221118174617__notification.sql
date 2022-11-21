create table notification
(
    id           bigint primary key auto_increment,
    title        varchar(50)  not null,
    content      varchar(100) not null,
    member_id    bigint       not null,
    `read`       tinyint,
    `type`       varchar(255),
    intent       json         not null,
    created_date datetime(6) not null
);

create index notification_member_id_read on notification (member_id, `read`);
