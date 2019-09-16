DELETE FROM users;
DELETE FROM menu_positions;
DELETE FROM votes;
DELETE FROM restaurants;
ALTER SEQUENCE users_seq            RESTART WITH 100000;
ALTER SEQUENCE menu_positions_seq   RESTART WITH 1000000;
ALTER SEQUENCE votes_seq            RESTART WITH 1000000;
ALTER SEQUENCE restaurants_seq      RESTART WITH 100000;

INSERT INTO restaurants (name) VALUES
    ('DoubleB'),
    ('floo'),
    ('Cezve'),
    ('Surf Coffee');

INSERT INTO users (name, email, password) VALUES
    ('John',      'johnycowboy@email.com',  '{noop}password'),
    ('Bob',       'builderbob@email.com',   '{noop}password'),
    ('Christine', 'chrstn@email.com',       '{noop}password'),
    ('Admin',     'admin@email.com',        '{noop}admin');

INSERT INTO user_roles (user_id, role) VALUES
(100000, 'ROLE_USER'),
(100001, 'ROLE_USER'),
(100002, 'ROLE_USER'),
(100003, 'ROLE_USER'),
(100003, 'ROLE_ADMIN');

INSERT INTO menu_positions (name, price, restaurant_id, date) VALUES
    ('Cappuccino',           500, 100000, current_date),
    ('Hario V60',            300, 100000, current_date),
    ('Almond croissant',     200, 100000, current_date),
    ('Latte',                400, 100001, current_date),
    ('Chemex',               200, 100001, current_date),
    ('Flat white',           450, 100003, current_date),
    ('Mokka',                550, 100003, current_date),
    ('Australian lungo',     300, 100003, current_date),
    ('Omelette',             230, 100003, current_date),
    ('Chicken sandwich',     250, 100003, current_date);

INSERT INTO votes (user_id, restaurant_id, date) VALUES
    (100000, 100003, current_date);