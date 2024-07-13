CREATE TABLE IF NOT EXISTS suppliers (
  id TEXT NOT NULL DEFAULT uuid_generate_v4(),
  code TEXT NOT NULL,
  name TEXT NOT NULL,
  description TEXT,
  address TEXT,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NULL,
  "version" int8 NOT NULL,
  is_active bool NOT NULL DEFAULT TRUE,
  deleted_at timestamptz NULL,
  CONSTRAINT suppliers_pk PRIMARY KEY (id)
 );
 
CREATE UNIQUE INDEX IF NOT EXISTS suppliers_un
  ON suppliers(code);
  
CREATE TABLE IF NOT EXISTS supplier_details (
  id TEXT NOT NULL DEFAULT uuid_generate_v4(),
  supplier_id TEXT NOT NULL,
  commodity_id TEXT NOT NULL,
  amount NUMERIC NOT NULL,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NULL,
  "version" int8 NOT NULL,
  deleted_at timestamptz NULL,
  CONSTRAINT supplier_details_pk PRIMARY KEY (id),
  CONSTRAINT supplier_details_fk_suppliers FOREIGN KEY(supplier_id) REFERENCES suppliers(id),
  CONSTRAINT supplier_details_fk_commodity FOREIGN KEY(commodity_id) REFERENCES commodities(id)  
 );
 
CREATE UNIQUE INDEX IF NOT EXISTS supplier_details_un
  ON supplier_details(supplier_id, commodity_id);