-- Table: public.account

-- DROP TABLE IF EXISTS public.account;

CREATE TABLE IF NOT EXISTS public.account
(
    id integer NOT NULL DEFAULT nextval('account_id_seq'::regclass),
    title character(20) COLLATE pg_catalog."default" NOT NULL,
    cost integer NOT NULL,
    CONSTRAINT account_id_key UNIQUE (id),
    CONSTRAINT account_cost_check CHECK (cost > '-1'::integer)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.account
    OWNER to postgres;