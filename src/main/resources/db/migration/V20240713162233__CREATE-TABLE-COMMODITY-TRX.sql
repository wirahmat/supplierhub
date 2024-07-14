CREATE TABLE IF NOT EXISTS commodity_trx (
  id TEXT NOT NULL DEFAULT uuid_generate_v4(),
  trx_number TEXT NOT NULL,
  trx_date DATE NOT NULL,
  user_id TEXT NOT NULL,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NULL,
  "version" int8 NOT NULL,
  deleted_at timestamptz NULL,
  CONSTRAINT commodity_trx_pk PRIMARY KEY (id),
  CONSTRAINT commodity_trx_fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);
 
CREATE UNIQUE INDEX IF NOT EXISTS commodity_trx_un
  ON commodity_trx(trx_number, trx_date);
  
CREATE TABLE IF NOT EXISTS commodity_trx_details (
  id TEXT NOT NULL DEFAULT uuid_generate_v4(),
  commodity_trx_id TEXT NOT NULL,
  supplier_id TEXT NOT NULL,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NULL,
  "version" int8 NOT NULL,
  deleted_at timestamptz NULL,
  CONSTRAINT commodity_trx_details_pk PRIMARY KEY (id),
  CONSTRAINT commodity_trx_details_fk_suppliers FOREIGN KEY(supplier_id) REFERENCES suppliers(id),
  CONSTRAINT commodity_trx_details_fk_commodity_trx FOREIGN KEY(commodity_trx_id) REFERENCES commodity_trx(id)
 );
 
CREATE UNIQUE INDEX IF NOT EXISTS commodity_trx_details_un
  ON commodity_trx_details(commodity_trx_id, supplier_id);
  
CREATE TABLE IF NOT EXISTS commodity_trx_subdetails (
  id TEXT NOT NULL DEFAULT uuid_generate_v4(),
  commodity_trx_detail_id TEXT NOT NULL,
  commodity_id TEXT NOT NULL,
  quantity INT NOT NULL,
  total_amount NUMERIC NOT NULL,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NULL,
  "version" int8 NOT NULL,
  deleted_at timestamptz NULL,
  CONSTRAINT commodity_trx_subdetails_pk PRIMARY KEY (id),
  CONSTRAINT commodity_trx_subdetails_fk_commodity_trx_detail FOREIGN KEY(commodity_trx_detail_id) REFERENCES commodity_trx_details(id),
  CONSTRAINT commodity_trx_subdetails_fk_commodity FOREIGN KEY(commodity_id) REFERENCES commodities(id)  
 );
 
CREATE UNIQUE INDEX IF NOT EXISTS commodity_trx_subdetails_un
  ON commodity_trx_subdetails(commodity_trx_detail_id, commodity_id);
