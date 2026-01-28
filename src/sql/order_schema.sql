CREATE TABLE mini_dish_management_app.order (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(100) NOT NULL,
    creation_datetime TIMESTAMP NOT NULL
);

CREATE TABLE mini_dish_management_app.dish_order(
    id SERIAL PRIMARY KEY,
    id_order INT NOT NULL,
    id_dish INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_id_order FOREIGN KEY (id_order) REFERENCES mini_dish_management_app.order(id),
    CONSTRAINT fk_id_dish FOREIGN KEY (id_dish) REFERENCES mini_dish_management_app.dish(id)
);