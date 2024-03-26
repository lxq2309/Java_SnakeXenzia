CREATE DATABASE SnakeXenziaDB
USE SnakeXenziaDB
CREATE TABLE score
(
	id int AUTO_INCREMENT,
  	name varchar(50),
  	score int,
  	CONSTRAINT PK_score PRIMARY KEY(id)
)