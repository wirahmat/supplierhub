CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS categories (
  id TEXT NOT NULL DEFAULT uuid_generate_v4(),
  code TEXT NOT NULL,
  name TEXT NOT NULL,
  description TEXT,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NULL,
  "version" int8 NOT NULL,
  is_active bool NOT NULL DEFAULT TRUE,
  deleted_at timestamptz NULL,
  CONSTRAINT categories_pk PRIMARY KEY (id)
 );
 
 CREATE UNIQUE INDEX IF NOT EXISTS categories_un
  ON categories(code);
