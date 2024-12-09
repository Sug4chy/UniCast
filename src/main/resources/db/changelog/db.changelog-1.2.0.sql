-- liquibase formatted sql

-- changeset sug4chy:1
BEGIN;

ALTER TABLE channel_chat
    RENAME TO telegram_chat;

ALTER TABLE telegram_chat
    ADD COLUMN type SMALLINT NOT NULL DEFAULT 0;

COMMIT;

-- changeset sug4chy:2
BEGIN;

CREATE TABLE IF NOT EXISTS academic_group
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS student
(
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name         TEXT UNIQUE NOT NULL,
    academic_group_id UUID REFERENCES academic_group (id),
    telegram_chat_id  UUID REFERENCES telegram_chat (id)
);

COMMIT;

-- changeset sug4chy:3
BEGIN;

ALTER TABLE telegram_chat
    ADD COLUMN current_scenario SMALLINT NULL;

ALTER TABLE telegram_chat
    ADD COLUMN current_state INT NULL;

ALTER TABLE telegram_chat
    ADD COLUMN current_scenario_args jsonb NULL;

COMMIT;