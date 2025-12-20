CREATE TYPE mini_dish_management_app.dish_type AS ENUM (
    'STARTER',
    'MAIN',
    'DESSERT'
);

CREATE TABLE mini_dish_management_app.Dish (
    id SERIAL PRIMARY KEY,
    name VARCHAR(70) NOT NULL,
    dish_type mini_dish_management_app.dish_type NOT NULL
    );

CREATE TYPE mini_dish_management_app.ingredient_category AS ENUM (
    'VEGETABLE',
    'ANIMAL',
    'MARINE',
    'DAIRY',
    'OTHER'
);

CREATE TABLE mini_dish_management_app.Ingredient (
    id SERIAL PRIMARY KEY,
    name VARCHAR(70) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    category mini_dish_management_app.ingredient_category NOT NULL,
    id_dish int,
    CONSTRAINT fk_id_dish FOREIGN KEY (id_dish) REFERENCES mini_dish_management_app.Dish(id)
);