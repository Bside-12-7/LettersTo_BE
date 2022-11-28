create table attendance
(
    id        bigint primary key auto_increment,
    member_id bigint not null,
    `date`    date   not null
);

create unique index attendance_member_id_date on attendance (member_id, `date`);
