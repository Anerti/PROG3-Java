package org.example;

import java.util.Objects;

public class Ingredients {

    private final int id;
    private String name;
    private Double price;
    private CategoryEnum category;
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

    void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getDishName() {
        return dish != null && dish.getName() != null ? dish.getName() : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredients)) return false;
        Ingredients that = (Ingredients) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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
