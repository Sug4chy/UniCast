-- liquibase formatted sql

-- changeset sug4chy:1
CREATE TABLE IF NOT EXISTS message_reaction
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message_id UUID NOT NULL REFERENCES sent_message (id),
    reaction INT NOT NULL,
    from_user TEXT NOT NULL
);

-- changeset sug4chy:2
ALTER TABLE message_reaction
ALTER COLUMN reaction
TYPE SMALLINT;