DROP TABLE IF EXISTS experienced_employee;
DROP TABLE IF EXISTS emp_dependent;

CREATE TABLE IF NOT EXISTS experienced_employee(id int PRIMARY KEY ,name varchar(200));

CREATE TABLE IF NOT EXISTS emp_dependent(empId int,name varchar(20),relation varchar(20),age int NULLABLE ,empId int PRIMARY KEY(empId),
                                                                                                                   FOREIGN KEY(empId) REFERENCES experienced_employee(id)
);