CREATE TABLE public.pong_log (
	id serial4 NOT NULL,
	message varchar NULL,
	creation_time timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT pong_log_pk PRIMARY KEY (id)
);