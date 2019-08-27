DELETE FROM users;
DELETE FROM menu_positions;
DELETE FROM votes;
DELETE FROM restaurants;
ALTER SEQUENCE users_seq            RESTART WITH 100000;
ALTER SEQUENCE menu_positions_seq   RESTART WITH 1000000;
ALTER SEQUENCE votes_seq            RESTART WITH 1000000;
ALTER SEQUENCE restaurants_seq      RESTART WITH 100000;

INSERT INTO restaurants (name) VALUES
    ('elBulli'),
    ('The Fat Duck'),
    ('Pierre Gagnaire'),
    ('KFC');

INSERT INTO users (name, email, password) VALUES
    ('John', 'johnycowboy@email.com', 'password'),
    ('Bob', 'builderbob@email.com', 'password'),
    ('Christine', 'chrstn@email.com', 'password'),
    ('Admin', 'admin@email.com', 'admin');

INSERT INTO user_roles (user_id, role) VALUES
(100000, 'ROLE_USER'),
(100001, 'ROLE_USER'),
(100002, 'ROLE_USER'),
(100003, 'ROLE_USER'),
(100003, 'ROLE_ADMIN');

INSERT INTO menu_positions (name, price, restaurant_id, date) VALUES
    ('Lobster gazpacho',        25000, 100000, current_date),
    ('Espuma de humo',          20000, 100000, current_date),
    ('Spherical melon caviar',  17000, 100000, current_date),
    ('The Mock Turtle soup',    15000, 100001, current_date),
    ('Mad tea',                 11000, 100001, current_date),
    ('Langoustine',             29000, 100002, current_date),
    ('Palamos',                 26500, 100002, current_date),
    ('Cocktail de poche',       9000,  100002, current_date),
    ('Basket',                  850,   100003, current_date),
    ('Spicy bites',             230,   100003, current_date),
    ('Twister original',        250,   100003, current_date),
    ('Chiefburger',             199,   100003, current_date),
    ('Coca-cola',               99,    100003, current_date);

INSERT INTO votes (user_id, restaurant_id, date) VALUES
    (100000, 100003, current_date);