DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (id, dateTime, description, calories)
VALUES (1,'2020-01-30 10:00', 'Завтрак', 500),
       (2,'2020-01-30 13:00', 'Обед', 1000),
       (3,'2020-01-30 20:00', 'Ужин', 500),
       (4,'2020-01-31 00:00', 'Еда на граничное значение', 100),
       (5,'2020-01-31 10:00', 'Завтрак', 1000),
       (6,'2020-01-31 13:00', 'Обед', 500),
       (7,'2020-01-31 20:00', 'Ужин', 410);