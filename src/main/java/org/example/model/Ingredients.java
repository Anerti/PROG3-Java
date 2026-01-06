package org.example.model;

import java.util.Objects;

public class Ingredients {

    private final int id;
    private final String name;
    private final Double price;
    private final CategoryEnum category;
    private Dish dish;

    public Ingredients(int id, String name, Double price, CategoryEnum category) {
        this.id = id;
        this.name = name;
        this.price = price != null ? price : 0.0;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        if (this.dish == dish) return;

        Dish oldDish = this.dish;
        this.dish = dish;

        if (oldDish != null) {
            oldDish.getIngredients().remove(this);
        }

        if (dish != null && !dish.getIngredients().contains(this)) {
            dish.getIngredients().add(this);
        }
    }


    public String getDishName() {
        return dish != null && dish.getName() != null ? dish.getName() : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredients that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", dishName='" + getDishName() + '\'' +
                '}';
    }
}
