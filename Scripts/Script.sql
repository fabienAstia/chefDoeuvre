CREATE TABLE t_admins(
   email VARCHAR(50),
   password VARCHAR(20),
   CONSTRAINT pk_t_admins PRIMARY KEY(email, password)
);

CREATE TABLE t_dimensions(
   dimension_name CHAR(1),
   CONSTRAINT pk_t_dimensions PRIMARY KEY(dimension_name)
);

CREATE TABLE t_questions(
   question_name VARCHAR(50),
   possible_values SMALLINT NOT NULL,
   email VARCHAR(50) NOT NULL,
   password VARCHAR(20) NOT NULL,
   PRIMARY KEY(question_name),
   FOREIGN KEY(dimension_name) REFERENCES t_dimensions(dimension_name),
   FOREIGN KEY(email, password) REFERENCES t_admins(email, password)
);

est ce que je mets un id technique à partir du MLD ?
