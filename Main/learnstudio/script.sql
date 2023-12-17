create table flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table flyway_schema_history
    owner to postgres;

create index flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table users
(
    id           serial
        constraint users_pk
            primary key,
    username     text   not null,
    email        text   not null,
    password     text   not null,
    role         text   not null,
    description  text,
    firstname    text,
    secondname   text,
    phone        text,
    registerdate bigint not null,
    lastauth     bigint
);

alter table users
    owner to postgres;

create table discipline
(
    id          serial
        constraint discipline_pk
            primary key,
    name        text not null,
    description text not null
);

alter table discipline
    owner to postgres;

create table student
(
    id           serial,
    userid       serial
        constraint student_users_id_fk
            references users
            on delete cascade,
    desciplineid serial
        constraint student_discipline_id_fk
            references discipline
            on delete cascade,
    result       smallint,
    year         smallint,
    constraint student_pk
        primary key (userid, desciplineid)
);

alter table student
    owner to postgres;

create table teacher
(
    id           serial,
    userid       serial
        constraint teacher_users_id_fk
            references users
            on delete cascade,
    desciplineid serial
        constraint teacher_discipline_id_fk
            references discipline
            on delete cascade,
    experience   smallint not null,
    students     smallint not null,
    rating       real     not null,
    constraint teacher_pk
        primary key (userid, desciplineid)
);

alter table teacher
    owner to postgres;

create table certificates
(
    id          serial,
    userid      serial
        constraint certificates_users_id_fk
            references users
            on delete cascade,
    certificate text not null,
    constraint certificates_pk
        primary key (userid, certificate)
);

alter table certificates
    owner to postgres;

create table course
(
    id          serial
        constraint course_pk
            primary key,
    name        text     not null,
    description text,
    cost        real     not null,
    discount    smallint not null,
    duration    integer  not null,
    active      boolean  not null
);

alter table course
    owner to postgres;

create table studygroups
(
    id          serial,
    name        text    not null
        constraint studygroups_pk
            primary key,
    description text    not null,
    courseid    integer not null
        constraint studygroups_course_id_fk
            references course
            on delete cascade
);

alter table studygroups
    owner to postgres;

create table usercourse
(
    id        serial,
    userid    serial
        constraint usercourse_users_id_fk
            references users,
    groupname text not null
        constraint usercourse_studygroups_name_fk
            references studygroups
            on delete cascade,
    start     bigint,
    enddate   bigint,
    attends   text,
    constraint usercourse_pk
        primary key (userid, groupname)
);

alter table usercourse
    owner to postgres;

create table news
(
    id         serial,
    title      text   not null,
    text       text   not null,
    preview    text   not null,
    background text,
    date       bigint not null
);

alter table news
    owner to postgres;


