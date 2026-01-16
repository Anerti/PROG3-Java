INSERT INTO mini_dish_management_app.Dish (id, name, dish_type)
VALUES ('1', 'Salade fraîche', 'STARTER'),
       ('2', 'Poulet grillé', 'MAIN'),
       ('3', 'Riz aux légumes', 'MAIN'),
       ('4', 'Gateâu au chocolat', 'DESSERT'),
       ('5', 'Salade de fruit', 'DESSERT');

INSERT INTO mini_dish_management_app.Ingredient (id, name, price, category, id_dish)
VALUES ('1', 'Laitue', '800.00', 'VEGETABLE', '1'),
       ('2', 'Tomate', '600.00', 'VEGETABLE', '1'),
       ('3', 'Poulet', '4500.00', 'ANIMAL', '2'),
       ('4', 'Chocolat', '3000.00', 'OTHER', '4'),
       ('5', 'Beurre', '2500.00', 'DAIRY', '4');

ALTER TABLE mini_dish_management_app.Dish
    ADD COLUMN selling_price NUMERIC(10,2);

UPDATE mini_dish_management_app.Dish SET selling_price = 3500.00 WHERE id = 1;

UPDATE mini_dish_management_app.Dish SET selling_price = 12000.00 WHERE id = 2;

UPDATE mini_dish_management_app.dish SET selling_price = null WHERE id = 3;

UPDATE mini_dish_management_app.dish SET selling_price = 8000.00 WHERE id = 4;

UPDATE mini_dish_management_app.dish SET selling_price = null WHERE id = 5;


