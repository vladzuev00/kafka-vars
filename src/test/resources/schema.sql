DROP TABLE IF EXISTS persons;

CREATE TABLE persons
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(256) NOT NULL,
    surname VARCHAR(256) NOT NULL,
    patronymic VARCHAR(256) NOT NULL,
    birth_date DATE NOT NULL
);

CREATE TABLE person_replications(
    id INTEGER PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    surname VARCHAR(256) NOT NULL,
    birth_date DATE NOT NULL,
    version INTEGER NOT NULL
);
