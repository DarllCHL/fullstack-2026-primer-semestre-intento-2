-- V3: La columna activo ya fue creada en V1
SELECT 1;
/*
SET @exists = (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'productos'
    AND COLUMN_NAME = 'activo'
);

SET @sql = IF(@exists = 0,
    'ALTER TABLE productos ADD COLUMN activo BOOLEAN DEFAULT TRUE',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

*/
