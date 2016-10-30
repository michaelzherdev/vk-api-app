DROP DATABASE IF EXISTS vkapiapp;
CREATE database vkapiapp;
\c vkapiapp;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS posts;

CREATE TABLE users
(
  id         INTEGER PRIMARY KEY NOT NULL,
  name       VARCHAR NOT NULL,
  lastname   VARCHAR NOT NULL
);


CREATE TABLE groups (
  id          INTEGER PRIMARY KEY NOT NULL,
  name        VARCHAR NOT NULL,
  subscribed  BOOL DEFAULT TRUE,
  link        VARCHAR NOT NULL,
  photo       VARCHAR
);

CREATE TABLE posts (
  id          INTEGER PRIMARY KEY NOT NULL,
  group_id    INTEGER NOT NULL,
  date        TIMESTAMP DEFAULT now(),
  text        VARCHAR,
  FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE
);