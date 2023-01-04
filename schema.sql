create database day34db;

use day34db;

create table registerees (
    id varchar(64) not null,
    name varchar(128) not null,
    tel varchar(64) not null,
    email varchar(64) PRIMARY KEY not null 
    -- primary key(userId);
);