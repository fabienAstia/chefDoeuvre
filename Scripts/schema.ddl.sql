DROP TABLE IF EXISTS t_dimensions, t_questions;

CREATE TABLE t_questions(
   id_question int GENERATED ALWAYS AS IDENTITY,
   question_name VARCHAR(50) NOT NULL,
   CONSTRAINT t_questions_pk PRIMARY KEY(id_question),
   CONSTRAINT t_questions_uk UNIQUE(question_name)
);
CREATE TABLE t_dimensions(
	id_dimension int GENERATED ALWAYS AS IDENTITY,
	dimension_name char(1) NOT NULL
	CONSTRAINT t_dimensions_pk PRIMARY KEY (id_dimension),
	CONSTRAINT t_questions_uk UNIQUE (dimension_name)
);
SELECT * FROM t_questions tq ;