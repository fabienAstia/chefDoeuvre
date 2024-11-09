DROP TABLE IF EXISTS t_dimensions, t_questions, t_users, t_scores, t_answers;

CREATE TABLE t_dimensions(
	id int GENERATED ALWAYS AS IDENTITY,
	name char(1) NOT NULL,
	CONSTRAINT t_dimensions_pk PRIMARY KEY (id),
	CONSTRAINT t_dimensions_uk UNIQUE (name)
);

CREATE TABLE t_questions (
	id int GENERATED ALWAYS AS IDENTITY,
	name varchar(70) NOT NULL,
	id_dimension int,
	CONSTRAINT t_questions_pk PRIMARY KEY (id),
	CONSTRAINT t_questions_uk UNIQUE (name),
	CONSTRAINT t_questions_dimensions_fk FOREIGN KEY (id_dimension)
		REFERENCES t_dimensions (id)
);
CREATE TABLE t_users (
	id int GENERATED ALWAYS AS IDENTITY,
	visitorUUID uuid,
	id_score int,
	CONSTRAINT t_users_pk PRIMARY KEY (id),
	CONSTRAINT t_users_visitorUUID_uk UNIQUE (visitorUUID),
	CONSTRAINT t_users_scores_uk UNIQUE (id_score)
);
CREATE TABLE t_scores(
	id int GENERATED ALWAYS AS IDENTITY,
	score_e numeric(5,2),
	score_i numeric(5,2),
	score_n numeric(5,2),
	score_s numeric(5,2),
	score_t numeric(5,2),
	score_f numeric(5,2),
	score_p numeric(5,2),
	score_j numeric(5,2),
	id_user int,
	CONSTRAINT t_scores_pk PRIMARY KEY (id),
	CONSTRAINT t_scores_users_fk FOREIGN KEY (id_user)
		REFERENCES t_users (id)
);
CREATE TABLE t_answers(
	id int GENERATED ALWAYS AS identity,
	id_question int,
	id_user int,
	rating int,
	CONSTRAINT t_answers_pk PRIMARY KEY (id),
	CONSTRAINT t_answers_questions_fk FOREIGN KEY (id_question)
		REFERENCES t_questions(id),
	CONSTRAINT t_answers_users_fk FOREIGN KEY (id_user)
		REFERENCES t_users (id)
);
SELECT * FROM t_questions tq ;
UPDATE t_questions SET id_dimension =2 WHERE id IN  (1, 2);