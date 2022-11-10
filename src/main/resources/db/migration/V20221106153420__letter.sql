alter table geolocation
    add column fullname varchar(255) not null, algorithm=inplace, lock=none;

update geolocation child
    inner join geolocation parent
on child.parent_id = parent.id
    set child.fullname = concat(parent.name, ' ', child.name)
where child.level = 3;

alter table member
    add column stamp_quantity int default 0, algorithm=inplace, lock=none;

create table stamp
(
    id    bigint primary key auto_increment,
    basic tinyint not null
);

insert into stamp(id, basic)
values (1, true),
       (2, true),
       (3, true),
       (4, true),
       (5, true),
       (6, true);

create table letter
(
    id                  bigint primary key auto_increment,
    dtype               varchar(255)  not null,
    title               varchar(255)  not null,
    content             varchar(1000) not null,
    paper_type          varchar(255)  not null,
    paper_color         varchar(255)  not null,
    stamp_id            bigint        not null,
    delivery_type       varchar(255),
    from_member_id      bigint        not null,
    to_member_id        bigint,
    from_geolocation_id bigint,
    to_geolocation_id   bigint,
    `read`              tinyint,
    replied             tinyint,
    delivery_date       datetime(6),
    created_date        datetime(6) not null,
    version             int           not null
);

create table picture
(
    id        bigint primary key auto_increment,
    file_id   varchar(255) not null,
    letter_id bigint
);

create table letter_topic
(
    id        bigint primary key auto_increment,
    letter_id bigint not null,
    topic_id  bigint not null
);

create table letter_personality
(
    id             bigint primary key auto_increment,
    letter_id      bigint not null,
    personality_id bigint not null
);

create table letter_box
(
    id             bigint primary key auto_increment,
    from_member_id bigint not null,
    to_member_id   bigint not null,
    created_date   datetime(6) not null
);

create unique index uix_letter_box_from_to on letter_box (from_member_id, to_member_id);
