DROP INDEX scene_characters_player_index;
DROP TABLE scene_characters;

CREATE TABLE scene_characters(
    scene INTEGER NOT NULL,
    character INTEGER NOT NULL
);

CREATE INDEX scene_characters_scene_index ON scene_characters(scene);
CREATE INDEX scene_characters_character_index ON scene_characters(character);

CREATE TABLE campaign_characters(
    campaign INTEGER NOT NULL,
    character INTEGER NOT NULL
);

CREATE INDEX campaign_characters_campaign_index ON campaign_characters(campaign);
CREATE INDEX campaign_characters_character_index ON campaign_characters(character);
