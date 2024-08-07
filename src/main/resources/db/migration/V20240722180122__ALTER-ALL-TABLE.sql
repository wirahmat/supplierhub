ALTER TABLE categories 
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP,
    ALTER COLUMN deleted_at TYPE TIMESTAMP;
    
ALTER TABLE commodities 
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP,
    ALTER COLUMN deleted_at TYPE TIMESTAMP;
    
ALTER TABLE users 
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP,
    ALTER COLUMN deleted_at TYPE TIMESTAMP;
    
ALTER TABLE suppliers 
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP,
    ALTER COLUMN deleted_at TYPE TIMESTAMP;
    
ALTER TABLE supplier_details 
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP,
    ALTER COLUMN deleted_at TYPE TIMESTAMP;
    
ALTER TABLE commodity_trx 
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP,
    ALTER COLUMN deleted_at TYPE TIMESTAMP;
    
ALTER TABLE commodity_trx_details 
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP,
    ALTER COLUMN deleted_at TYPE TIMESTAMP;
    
ALTER TABLE commodity_trx_subdetails 
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN updated_at TYPE TIMESTAMP,
    ALTER COLUMN deleted_at TYPE TIMESTAMP;