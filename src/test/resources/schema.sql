--DROP TABLE IF EXISTS employees;
--DROP TABLE IF EXISTS emp_dependent;

CREATE TABLE  experienced_employee(id int PRIMARY KEY ,name varchar(200),experience double);

CREATE TABLE  emp_dependent(empId int,name varchar(20) PRIMARY KEY,relation varchar(20),age int ,FOREIGN KEY(empId) REFERENCES experienced_employee(id));
--IF NOT EXISTS