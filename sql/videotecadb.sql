DROP SCHEMA IF EXISTS videotecadb;
DROP USER IF EXISTS 'spq'@'localhost';

CREATE SCHEMA videotecadb;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';

GRANT ALL ON videotecadb.* TO 'spq'@'localhost';
