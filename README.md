## MCD

![image](https://github.com/user-attachments/assets/fb31d8f8-1836-4bf1-895e-482d5cdecb7c)

## MLD

![image](https://github.com/user-attachments/assets/9a659da0-5e5e-40bb-a528-794ebed7661d)

## MPD

```postgreSQL
CREATE TABLE t_dimensions(
	id int GENERATED ALWAYS AS IDENTITY,
	name char(1) NOT NULL,
	CONSTRAINT t_dimensions_pk PRIMARY KEY (id),
	CONSTRAINT t_dimensions_uk UNIQUE (name)
);

CREATE TABLE t_questions (
	id int GENERATED ALWAYS AS IDENTITY,
	text varchar(255) NOT NULL,
	dimension_id int,
	CONSTRAINT t_questions_pk PRIMARY KEY (id),
	CONSTRAINT t_questions_uk UNIQUE (text),
	CONSTRAINT t_questions_dimensions_fk FOREIGN KEY (dimension_id)
		REFERENCES t_dimensions (id)
);
CREATE TABLE t_users (
	id int GENERATED ALWAYS AS IDENTITY,
	email varchar(255) NOT NULL,
	password varchar(60) NOT NULL,
	role varchar(5) NOT NULL,
	CONSTRAINT t_users_pk PRIMARY KEY (id),
	CONSTRAINT t_users_email_uk UNIQUE (email)
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
	user_id int,
	CONSTRAINT t_scores_pk PRIMARY KEY (id),
	CONSTRAINT t_scores_users_fk FOREIGN KEY (user_id)
		REFERENCES t_users (id)
);

CREATE TABLE t_answers(
	id int GENERATED ALWAYS AS IDENTITY,
	user_id int,
	question_id int,
	score_id int,
	rating int,
	CONSTRAINT t_answers_pk PRIMARY KEY (id),
	CONSTRAINT t_answers_users_fk FOREIGN KEY (user_id)
		REFERENCES t_users(id),
	CONSTRAINT t_answers_questions_fk FOREIGN KEY (question_id)
		REFERENCES t_questions(id),
	CONSTRAINT t_answers_scores_fk FOREIGN KEY (score_id)
		REFERENCES t_scores(id)
);
```

