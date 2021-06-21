--primary tables -------------------------------

CREATE TABLE users(
    id serial primary key,
	user_code varchar(20) unique not null,
	full_name varchar(300) not null,
    email varchar(150) not null,
	password varchar(300) not null,
	role varchar(50) not null,
	status smallint not null,
	date_created timestamp not null,
	date_updated timestamp
);

CREATE TABLE admin(
    id serial primary key,
    id_user int not null,
    FOREIGN KEY (id_user) REFERENCES users (id)
);

CREATE TABLE teacher(
    id serial primary key,
    id_user int not null,
    grade varchar(50) not null,
    phone varchar(10) not null,
    FOREIGN KEY (id_user) REFERENCES users (id)
);

CREATE TABLE company(
    id serial primary key,
    id_user int not null,
    name varchar(100) not null,
    service_type varchar(150) not null,
    size_company varchar(75) not null,
    address varchar(300) not null,
    hr_name varchar(150) not null,
    hr_lastname varchar(150) not null,
    hr_phone varchar(10) not null,
    hr_email varchar(150) not null,
    FOREIGN KEY (id_user) REFERENCES users (id)
);

--secondary tables --------------------------------

CREATE TABLE advisor(
    id serial primary key,
    id_company int not null,
    name varchar(150) not null,
    lastname varchar(150) not null,
    grade varchar(50) not null,
    phone varchar(10) not null,
    email varchar(150) not null,
    status smallint not null,
    date_created timestamp not null,
	date_updated timestamp,
    FOREIGN KEY (id_company) REFERENCES company (id)
);

CREATE TABLE class(
    id serial primary key,
    carreer_name varchar(300) not null,
    schedule varchar(200) not null,
    grade varchar(20) not null,
    group_class varchar(20) not null,
	status smallint not null,
    date_created timestamp not null,
	date_updated timestamp
);

CREATE TABLE classroom(
    id serial primary key,
    id_class int not null,
    id_teacher int not null,
    code varchar(20) not null,
    carreer_name varchar(300) not null,
    schedule varchar(200) not null,
    grade varchar(20) not null,
    group_classroom varchar(20) not null,
    status smallint not null,
    date_created timestamp not null,
	date_updated timestamp,
    FOREIGN KEY (id_class) REFERENCES class (id),
	FOREIGN KEY (id_teacher) REFERENCES teacher (id)
    
);

CREATE TABLE student(
    id serial primary key,
    id_user int not null,
    id_classroom int not null,
    id_advisor int not null,
    enrollment varchar(15) not null,
    FOREIGN KEY (id_user) REFERENCES users (id),
    FOREIGN KEY (id_classroom) REFERENCES classroom (id),
    FOREIGN KEY (id_advisor) REFERENCES advisor (id)
);

CREATE TABLE project(
    id serial primary key,
    id_company int not null,
    name varchar(100) not null,
    objectives varchar(500) not null,
    status smallint not null,
    date_created timestamp not null,
	date_updated timestamp,
    FOREIGN KEY (id_company) REFERENCES company (id)
);

CREATE TABLE register(
    id serial primary key,
    id_student int not null,
    id_project int not null,
    date_register date not null, 
    time_register time not null,
    description varchar(500) not null,
    status smallint not null,
    date_created timestamp not null,
	date_updated timestamp,
    FOREIGN KEY (id_student) REFERENCES student (id),
    FOREIGN KEY (id_project) REFERENCES project (id)
);

CREATE TABLE news(
    id serial primary key,
    id_admin int not null,
    title varchar(100),
    description varchar(1000),
    date_start date not null,
    date_end date not null,
    status smallint not null,
    date_created timestamp not null,
	date_updated timestamp,
    FOREIGN KEY (id_admin) REFERENCES admin (id)
);

--many to many tables-------------------------------

CREATE TABLE student_project(
    id serial primary key,
    id_student int not null,
    id_project int not null,
    status smallint not null,
    date_created timestamp not null,
	date_updated timestamp,
    FOREIGN KEY (id_student) REFERENCES student (id),
    FOREIGN KEY (id_project) REFERENCES project (id)
);

CREATE TABLE news_classroom(
    id serial primary key,
    id_news int not null,
    id_classroom int not null,
    status smallint not null,
    date_created timestamp not null,
	date_updated timestamp,
    FOREIGN KEY (id_news) REFERENCES news (id),
    FOREIGN KEY (id_classroom) REFERENCES classroom (id)
);