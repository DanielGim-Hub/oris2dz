CREATE TABLE driver(
    id serial primary key,
    name VARCHAR(50),
    surname VARCHAR(50),
    age VARCHAR(10)
);
ALTER SEQUENCE driver_id_seq RESTART WITH 1;  -- Сброс автоинкремента
