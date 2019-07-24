CREATE SCHEMA IF NOT EXISTS commands;

-- Table for !wins, !addwins, !clearwins
DROP TABLE IF EXISTS commands.games;

CREATE TABLE commands.games (
    game_id SERIAL PRIMARY KEY,
    game_title VARCHAR (150) NOT NULL,
    game_abbr VARCHAR (25) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT (NOW()),
    modified_on TIMESTAMP NOT NULL DEFAULT (NOW())
);

INSERT INTO commands.games (game_title, game_abbr)
VALUES ('Apex Legends', 'apex')
     , ('PUBG', 'pubg');

DROP TABLE IF EXISTS commands.game_wins;

CREATE TABLE commands.game_wins (
    game_win_id SERIAL PRIMARY KEY REFERENCES commands.games(game_id) ON DELETE CASCADE,
    win_count INT NOT NULL DEFAULT (0),
    created_on TIMESTAMP NOT NULL DEFAULT (NOW()),
    modified_on TIMESTAMP NOT NULL DEFAULT (NOW())
);

INSERT INTO commands.game_wins (game_win_id)
VALUES (1), (2);