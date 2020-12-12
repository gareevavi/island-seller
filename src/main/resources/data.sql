INSERT INTO region (id, name, ocean)
VALUES (1, 'Canadian', 'Arctic'),
       (2, 'Hawaiian', 'Pacific'),
       (3, 'Caribbian', 'Atlantic');

INSERT INTO country (id, name)
VALUES (1, 'France'),
       (2, 'USA'),
       (3, 'Canada'),
       (4, 'Antigua');

INSERT INTO islands (id, name, region_id, country_id, price_eu, size_acr)
VALUES (1, 'Loco-boco', 2, 2, 40000, 3),
       (2, 'Halora bay', 3, 4, 320000, 32),
       (3, 'Woodgrass', 1, 3, 2740000, 127);
