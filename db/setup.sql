-- Create monitoring database and user
CREATE DATABASE IF NOT EXISTS monitoring;
CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON admin.* TO 'admin'@'%';

-- Create test database and user
CREATE DATABASE IF NOT EXISTS test;
CREATE USER IF NOT EXISTS 'test_user'@'%' IDENTIFIED BY 'test_password';
GRANT ALL PRIVILEGES ON test.* TO 'test_user'@'%';

FLUSH PRIVILEGES;