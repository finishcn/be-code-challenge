CREATE TABLE public.pong_message (
	id serial4 NOT NULL, -- id
	trace_id varchar NULL, -- service trace
	ping_message varchar NULL, -- ping send
	pong_message varchar NULL, -- pong response
	creation_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- create tiem
	CONSTRAINT pong_log_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.pong_message IS 'test message';

-- Column comments

COMMENT ON COLUMN public.pong_message.id IS 'id';
COMMENT ON COLUMN public.pong_message.trace_id IS 'service trace';
COMMENT ON COLUMN public.pong_message.ping_message IS 'ping send';
COMMENT ON COLUMN public.pong_message.pong_message IS 'pong response';
COMMENT ON COLUMN public.pong_message.creation_time IS 'create tiem';