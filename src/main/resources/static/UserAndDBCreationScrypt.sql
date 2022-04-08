-- CREATE USER "postgres" WITH PASSWORD '1234' CREATEDB;

-- CREATE DATABASE postgres
--     WITH
--     OWNER = "postgres"
--     ENCODING = 'UTF8'
--     CONNECTION LIMIT = -1;

CREATE SCHEMA school
    AUTHORIZATION "postgres";
