CREATE TABLE IF NOT EXISTS commodities (
  id TEXT NOT NULL DEFAULT uuid_generate_v4(),
  code TEXT NOT NULL,
  name TEXT NOT NULL,
  description TEXT,
  restock_when INT,
  quantity INT NOT NULL DEFAULT 0,
  registered_date DATE NOT NULL,
  category_id TEXT NOT NULL,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NULL,
  "version" int8 NOT NULL,
  is_active bool NOT NULL DEFAULT TRUE,
  deleted_at timestamptz NULL,
  CONSTRAINT commodities_pk PRIMARY KEY (id),
  CONSTRAINT commodities_fk_category FOREIGN KEY(category_id) REFERENCES categories(id) 
 );
 
 CREATE UNIQUE INDEX IF NOT EXISTS commodities_un
  ON commodities(code);