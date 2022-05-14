CREATE TABLE characters(
    id INTEGER PRIMARY KEY,
    player INTEGER NOT NULL,
    name TEXT NOT NULL,
    created_at TEXT NOT NULL
);

CREATE INDEX characters_player_index ON characters(player);

CREATE TABLE scenes(
    id INTEGER PRIMARY KEY,
    campaign INTEGER NOT NULL,
    name TEXT NOT NULL,
    created_at TEXT NOT NULL
);

CREATE INDEX scenes_campaign_index ON scenes(campaign);

CREATE TABLE scene_characters(
    scene INTEGER UNIQUE,
    character INTEGER NOT NULL
)

CREATE INDEX scene_characters_player_index ON scene_characters(character);
