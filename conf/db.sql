use chatroomz;

drop table if exists notifications;

create table notifications (
    id char(36) unique not null,
    room varchar(50) not null,
    username varchar(50) not null,
    message varchar(300) not null,
    timestamp datetime not null
);
