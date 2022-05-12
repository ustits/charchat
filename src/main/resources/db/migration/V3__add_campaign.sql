CREATE TABLE campaigns(
    id INTEGER PRIMARY KEY,
    dm INTEGER NOT NULL,
    name TEXT NOT NULL,
    created_at TEXT NOT NULL
);

CREATE INDEX campaign_dm_index ON campaigns(dm);
