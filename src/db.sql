
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

insert into user values
(3,"Metro Boomin","123456"),
(114514,"J.Cole","mission"),
(21,"21 Savage","pussy");
(7,"Kendrick Lamar","goat");

insert into blog values
(1,"Up to Something"," CAPES (Deluxe) [Explicit]",now(),3),
(2,"Neighbor","cole is on the mission",now(),114514),
(3,"10 Freaky Girl","let 21 tell me you're a pussyyyyyyy~",now(),21);