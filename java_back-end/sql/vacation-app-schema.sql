-- create tables and relationships
drop database if exists vacation_app;
create database vacation_app;

use vacation_app;

-- create location
create table location (
	location_id int primary key auto_increment,
	description varchar(50) not null,
    latitude float(10, 4) not null,
    longitude float(10, 4) not null
);

-- create vacation
create table vacation (
	vacation_id int primary key auto_increment,
	description varchar(250) not null,
	leasure_level int not null
);

-- create user
create table user (
	user_id int primary key auto_increment,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	user_name varchar(50) not null unique,
	password varchar(50) not null,
	user_type tinyint not null
);

-- create comment
create table comment (
	comment_id int primary key auto_increment,
	text varchar(250) not null,
	user_id int not null,
	vacation_id int not null,
	constraint fk_comment_user_id
        foreign key (user_id)
        references user(user_id),
	constraint fk_comment_vacation_id
        foreign key (vacation_id)
        references vacation(vacation_id)
);

-- create vacation_user
create table vacation_user (
	vacation_id int not null,
	user_id int not null,
	identifier varchar(50) not null,
	constraint pk_vacation_user
        primary key (user_id, vacation_id),
	constraint fk_vacation_user_user_id
        foreign key (user_id)
        references user(user_id),
	constraint fk_vacation_user_vacation_id
        foreign key (vacation_id)
        references vacation(vacation_id),
	constraint uq_vacation_user_identifier_vacation_id
        unique (identifier, vacation_id)
);

-- create vacation_stops
create table vacation_stops (
	vacation_id int not null,
    location_id int not null,
    start_date date not null,
    end_date date not null,
    identifier varchar(50) not null,
    constraint pk_vacation_stops
        primary key (vacation_id, location_id),
	constraint fk_vacation_stops_location_id
        foreign key (location_id)
        references location(location_id),
	constraint fk_vacation_stops_vacation_id
        foreign key (vacation_id)
        references vacation(vacation_id),
	constraint uq_vacation_stops_identifier_vacation_id
        unique (identifier, vacation_id)
);

-- inserts
use vacation_app;
	
-- location inserts
insert into location
	(location_id, description, latitude, longitude)
values
	(1, 'St.Paul, MN', 44.9537, -93.0900),
	(2, 'Madison, WI', 43.0731, -89.4012),
	(3, 'Orlando, FL', 28.5383, -81.3792),
	(4, 'New York, NY', 40.7128, -74.0060),
	(5, 'Los Angeles, CA', 34.0522, -118.2437),
	(6, 'Honolulu, HI', 21.3069, -157.8583);

-- user inserts
insert into user
	(user_id, first_name, last_name, user_name, password, user_type)
values
	(1, 'admin', 'admin', 'admin', 'admin#123', 0);
	
-- vacation inserts
insert into vacation
	(vacation_id, description, leasure_level)
values
	(1, 'A trip to the capital of MN', 1),
	(2, 'Halloween Fun Trip', 1),
	(3, 'Disney World Tour Trip', 2),
	(4, 'New Years Trip', 2),
	(5, 'Hollywood Trip', 3),
	(6, 'Beaches and Sun Trip', 3);
	
-- comment inserts
insert into comment
	(comment_id, text, user_id, vacation_id)
values
	(1, 'Was a very boring and uneventful trip.', 1, 1),
	(2, 'Had a blast and was a lot of fun.', 1, 3),
	(3, 'Got to lay on the beach and relax.', 1, 6);

-- vacation_user inserts
insert into vacation_user
	(vacation_id, user_id, identifier)
select
	vacation.vacation_id,
	user.user_id,
	concat(vacation.vacation_id, '-', user.user_id)
from vacation
inner join user
where user.user_id = 1
and vacation.vacation_id != 2;

-- vacation_stops inserts
insert into vacation_stops
	(vacation_id, location_id, start_date, end_date, identifier)
values
	(1, 1, '2021-07-04', '2021-07-11', concat(vacation_id, '-', location_id)),
    (2, 2, '2021-08-04', '2021-08-11', concat(vacation_id, '-', location_id)),
    (3, 3, '2021-09-04', '2021-09-11', concat(vacation_id, '-', location_id)),
    (4, 4, '2021-10-04', '2021-10-11', concat(vacation_id, '-', location_id));
    
    
    
    
    
    
    
    