
//https://github.com/outworkers/phantom
//https://mvnrepository.com/artifact/com.outworkers

//====================================================
//
//casssandra 3.11.4
//
//====================================================

CREATE KEYSPACE prj
WITH durable_writes = true
AND replication = {
	'class' : 'SimpleStrategy',
	'replication_factor' : 1
}

CREATE TABLE prj.users(
	id    bigint,
	name  text,
	email text,
	edomain text,
	PRIMARY KEY (id)
);


insert into prj.users(id,name,email,edomain) values(1,'Ivan','Ivan@mail.ru','@mail.ru');
insert into prj.users(id,name,email,edomain) values(2,'Petr','Petr@gmail.com','@gmail.com');
insert into prj.users(id,name,email,edomain) values(3,'Mark','Mark@ya.ru','@ya.ru');

select * from prj.users;

//====================================================
//
//postgres PostgreSQL 11.1   select version()
//
//====================================================

create database prj;
create user prj with encrypted password 'prj';
grant all privileges on database prj to prj;






drop table users;

delete from users;

select edomain,sum(1) as cnt from users group by edomain;

select sum(1) as cnt from users;

select * from users;





drop table prj.users;

create table prj.users(
	id bigint,
	name text,
	email text,
	edomain text,
	primary key(id)
);

truncate prj.users;

select * from prj.users;

select min(id) from prj.users;

select count(*) from prj.users;