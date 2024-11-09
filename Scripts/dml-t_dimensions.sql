DELETE FROM t_dimensions;

INSERT INTO t_dimensions (name) VALUES 
	('E'),
	('I'),
	('N'),
	('S'),
	('T'),
	('F'),
	('P'),
	('J');
SELECT * FROM t_dimensions td ;

--UPDATE t_questions SET question_dimension_id = 3  WHERE id_question = 24;