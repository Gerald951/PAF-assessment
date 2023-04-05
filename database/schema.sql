drop database if exists bgg;

create database bgg;

use bgg;

create table user_ (
    user_id varchar(8) not null,
    username char(50) not null,
    friendly_name varchar(50),
    constraint user_pk primary key(user_id)
);

create table task (
	task_id int not null auto_increment,
    user_id_fk varchar(8) not null,
    task_description char(255) not null,
    priority int not null,
    due_date date not null,
    constraint task_pk primary key(task_id),
    constraint task_user_fk foreign key(user_id_fk) references user_(user_id)
);

load data infile './data.csv'
into table user_
fields terminated by ','
lines terminated by '\n'
ignore 1 rows;
