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

