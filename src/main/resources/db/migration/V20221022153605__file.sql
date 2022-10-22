create table file
(
    id           varchar(255) primary key,
    bucket       varchar(255) not null,
    `key`        varchar(255) not null,
    verified     tinyint      not null,
    expired_date datetime(6) not null,
    created_date datetime(6) not null
);

create unique index uix_bucket_key on file (bucket, `key`);
