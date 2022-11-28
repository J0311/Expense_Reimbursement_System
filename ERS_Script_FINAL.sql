create table employee(
e_id serial,
first_name varchar(100),
last_name varchar(100),
username varchar(100),
password varchar(100),
email varchar(100),
primary key (e_id)
);

create table manager(
m_id serial,
first_name varchar(100),
last_name varchar(100),
username varchar(100),
password varchar(100),
email varchar(100),
primary key (m_id)
);

create table reimbursement(
r_id serial,
amount numeric (4,2),
e_id int,
m_id int,
status varchar(100),
reimbursement_type varchar(100),
primary key (r_id)
);

drop table student;
drop table employee;
drop table manager;
drop table reimbursement;

insert into employee (first_name, last_name, username, password, email)
values ('Mark', 'Murphy', 'MandM','password','murphy@aol.com'),
('Todd', 'Clark', 'TClark','password','todd@gmail.com'),
('Emily', 'Smith', 'ESmith','password','emily@yahoo.com'),
('Tim', 'Beaver', 'TBtheGreat','password','tim@aol.com');

select * from employee;

insert into manager (first_name, last_name, username, password, email)
values ('Miguel', 'Streety', 'MtheStreet','password','miguel@aol.com'),
('Andre', 'Ward', 'SOG','password','andre@gmail.com'),
('Phil', 'Myers', 'PhilTheThrill','password','phil@yahoo.com'),
('Mike', 'Jones', 'WhoIsMike','password','mj@gmail.com');

select * from manager;

insert into reimbursement(amount, e_id, m_id, status, reimbursement_type) 
values (48.00, 1, 3, 'pending', 'parking'),
(88.00, 2, 2, 'pending', 'travel'),
(60.70, 4, 1, 'denied', 'food'),
(73.01, 3, 4, 'approved', 'lodging');

select * from reimbursement;
