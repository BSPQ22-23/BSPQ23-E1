DROP SCHEMA IF EXISTS videoclubdb;
DROP USER IF EXISTS 'spq'@'localhost';

CREATE SCHEMA videoclubdb;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';

GRANT ALL ON videoclubdb.* TO 'spq'@'localhost';
