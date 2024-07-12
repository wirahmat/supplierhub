CREATE TABLE IF NOT EXISTS users (
  id TEXT NOT NULL DEFAULT uuid_generate_v4(),
  full_name TEXT NOT NULL,
  email TEXT NOT NULL,
  password TEXT NOT NULL,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NULL,
  "version" int8 NOT NULL,
  is_active bool NOT NULL DEFAULT TRUE,
  deleted_at timestamptz NULL,
  CONSTRAINT users_pk PRIMARY KEY (id)
 );
 
 CREATE UNIQUE INDEX IF NOT EXISTS users_un
  ON users(email);