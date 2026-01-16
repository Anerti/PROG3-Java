CREATE DATABASE mini_dish_db;
CREATE ROLE mini_dish_db_manager LOGIN ENCRYPTED PASSWORD 'HdtPa124eSecure' NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT;
\c mini_dish_db
CREATE SCHEMA mini_dish_management_app AUTHORIZATION mini_dish_db_manager;
GRANT USAGE ON SCHEMA mini_dish_management_app TO mini_dish_db_manager;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA mini_dish_management_app TO mini_dish_db_manager;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA mini_dish_management_app TO mini_dish_db_manager;