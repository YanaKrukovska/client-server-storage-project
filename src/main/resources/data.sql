INSERT INTO groups (id, name, description)
VALUES (1, 'God Food', 'Food only gods eat'),
       (2, 'Dairy', 'Type of food produced from or containing the milk of mammals'),
       (3, 'Literature', 'Books, magazines, etc');

INSERT INTO products (id, name, group_id, producer, price, amount, description)
VALUES (1, 'Buckwheat', 1, 'God Company', 10000, 1, 'Worlds best buckwheat'),
       (2, 'Milk', 2, 'Prostokvashino', 52.0, 1500, 'Cow milk'),
       (3, 'Butter', 2, 'Yahotynske', 23.46, 500, 'Butter 72%'),
       (4, 'Booblyk V. - OOP', 3, 'Booblyk Company', 999.99, 1, 'Worlds best book about OOP');

INSERT INTO users (id, username, email, PASSWORD)
VALUES (1, 'OlegVynnyk', 'olegvynnyk@gmail.com', '$2a$10$vx65JDXg5cH.40IwIh01eOmShF6xTuZ8ujE48kzWBdnXaRoccl69m');

INSERT INTO user_roles (id, name)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO users_roles (users_id, roles_id)
VALUES (1, 1);