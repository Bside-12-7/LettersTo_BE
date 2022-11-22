create table stamp_history
(
    id           bigint primary key auto_increment,
    quantity     int    not null,
    member_id    bigint not null,
    `type`       varchar(255),
    created_date datetime(6) not null
);

create index stamp_history_member_id_created_date on stamp_history (member_id, created_date);
