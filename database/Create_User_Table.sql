create table users
(
	userid varchar(40) null,
	userName varchar(20) not null,
	email varchar(40) not null,
	gamesOwned int default 0 null
);

create unique index users_userName_uindex
	on users (userName);

create unique index users_userid_uindex
	on users (userid);

alter table users
	add constraint users_pk
		primary key (userid);

