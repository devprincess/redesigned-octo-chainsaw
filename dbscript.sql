create database socialnet;


create table user (
id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(20),
mobile varchar(10),
email varchar(50),
pwd varchar(20),
gender varchar(1),
birthdate date
);


INSERT INTO user(name, mobile, email, pwd, gender, birthdate) VALUES ('Joana Chavez', '3489245521', 'admin', '123456', 'F', '1988-08-10');
