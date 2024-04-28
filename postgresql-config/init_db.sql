CREATE DATABASE historydb;

\c historydb;

CREATE TABLE request_history (
    id SERIAL PRIMARY KEY,
    website_url VARCHAR(255) NOT NULL,
    summary TEXT
);

CREATE USER root WITH PASSWORD 'root' SUPERUSER; 


