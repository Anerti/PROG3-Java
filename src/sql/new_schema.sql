CREATE TYPE mini_dish_management_app.unit_type AS ENUM (
    'PCS',
    'KG',
    'L'
    );

CREATE TABLE mini_dish_management_app.DishIngredient (
    id SERIAL PRIMARY KEY,
    id_dish INT,
    id_ingredient INT,
    quantity_required NUMERIC(10, 2),
    unit mini_dish_management_app.unit_type,
    CONSTRAINT fk_id_dish FOREIGN KEY (id_dish) REFERENCES mini_dish_management_app.Dish(id),
    CONSTRAINT fk_id_ingredient FOREIGN KEY (id_ingredient) REFERENCES mini_dish_management_app.ingredient
);

INSERT INTO mini_dish_management_app.DishIngredient (id, id_dish, id_ingredient, quantity_required, unit)
VALUES (1, 1, 1, 0.20, 'KG'),
       (2, 1, 2, 0.15, 'KG'),
       (3, 2, 3, 1.00, 'KG'),
       (4, 4, 4, 0.30, 'KG'),
       (5, 4, 5, 0.20, 'KG');

ALTER TABLE mini_dish_management_app.ingredient DROP COLUMN IF EXISTS id_dish;

CREATE TYPE mini_dish_management_app.movement_type AS ENUM (
    'IN', 'OUT'
    );

CREATE TABLE mini_dish_management_app.StockMovement(
    id SERIAL PRIMARY KEY,
    id_ingredient INT NOT NULL,
    quantity NUMERIC(10, 2) NOT NULL,
    type mini_dish_management_app.movement_type NOT NULL,
    unit mini_dish_management_app.unit_type NOT NULL,
    creation_datetime TIMESTAMP NOT NULL
);

INSERT INTO mini_dish_management_app.StockMovement(id, id_ingredient, quantity, type, unit, creation_datetime)
VALUES(1, 1, 5.0, 'IN', 'KG', '2024-01-05 08:00'),
      (2, 1, 0.2, 'OUT', 'KG', '2024-01-06 12:00'),
      (3, 2, 4.0, 'IN', 'KG', '2024-01-05 08:00'),
      (4, 2, 0.15, 'OUT', 'KG', '2024-01-06 12:00'),
      (5, 3, 10.0, 'IN', 'KG', '2024-01-04 09:00'),
      (6, 3, 1.0, 'OUT', 'KG', '2024-01-06 13:00'),
      (7, 4, 3.0, 'IN', 'KG', '2024-01-05 10:00'),
      (8, 4, 0.3, 'OUT', 'KG', '2024-01-06 14:00'),
      (9, 5, 2.5, 'IN', 'KG', '2024-01-05 10:00'),
      (10, 5, 0.2, 'OUT', 'KG', '2024-01-06 14:00');
