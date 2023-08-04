
create database if not exists blog_system;
use blog_system;

drop table if exists user;
drop table if exists blog;

create table user(
userId int primary key auto_increment,
username varchar(20) unique,
password varchar(20)
);

create table blog(
blogId int primary key auto_increment,
title varchar(128),
content varchar(4096),
postTime datetime,
userId int,
foreign key(userId) references user(userId)
);


