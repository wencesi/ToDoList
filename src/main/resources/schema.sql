drop table if exists todo;


create table todo (
	id integer not null, 
	title varchar(100) not null,	
	description varchar(300) not null,
	primary key(id)
	);