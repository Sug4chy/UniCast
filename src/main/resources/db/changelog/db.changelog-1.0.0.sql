-- liquibase formatted sql

-- changeset sug4chy:1
CREATE TABLE IF NOT EXISTS channel_chat
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ext_id BIGINT NOT NULL UNIQUE,
    name TEXT NOT NULL UNIQUE,
    added_at TIMESTAMP NOT NULL
);