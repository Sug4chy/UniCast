-- liquibase formatted sql

-- changeset test:1
CREATE TABLE IF NOT EXISTS example_table
(
    id INT PRIMARY KEY,
    name TEXT NOT NULL
);