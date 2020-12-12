CREATE TABLE region
(
    id    INTEGER PRIMARY KEY AUTO_INCREMENT,
    name  TEXT NOT NULL,
    ocean TEXT
);

CREATE TABLE country
(
    id   INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE islands
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    name       TEXT    NOT NULL,
    region_id  INTEGER NOT NULL REFERENCES region,
    country_id INTEGER NOT NULL REFERENCES country,
    price_eu   INTEGER NOT NULL CHECK (price_eu > 0),
    size_acr   INTEGER NOT NULL DEFAULT 1 CHECK (size_acr > 0)
);
