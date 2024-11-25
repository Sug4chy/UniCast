-- liquibase formatted sql

-- changeset sug4chy:1
CREATE TABLE IF NOT EXISTS channel_chat
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ext_id   BIGINT    NOT NULL UNIQUE,
    name     TEXT      NOT NULL UNIQUE,
    added_at TIMESTAMP NOT NULL
);

-- changeset sug4chy:2
CREATE TABLE IF NOT EXISTS sent_message
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ext_id  INT  NOT NULL,
    text    TEXT NOT NULL,
    chat_id UUID NOT NULL REFERENCES channel_chat (id),
    sender  TEXT NOT NULL,
    UNIQUE (ext_id, chat_id)
);