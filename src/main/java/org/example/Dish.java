package org.example;

import java.util.List;
import java.util.Objects;

public class Dish {
    private int id;
    private String name;
    private DishTypeEnum dishType;
    private List<Ingredients> ingredients;

    public Dish(int id, String name, DishTypeEnum dishType, List<Ingredients> ingredients) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.ingredients = ingredients;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public DishTypeEnum getDishType() {
        return dishType;
    }
    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDishType(DishTypeEnum dishType) {
        this.dishType = dishType;
    }
    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public Double getDishPrice() {
        Double output = 0.0;
        for (Ingredients ingredient : this.ingredients)
            output += ingredient.getPrice();
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id && Objects.equals(name, dish.name) && dishType == dish.dishType && Objects.equals(ingredients, dish.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dishType, ingredients);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishType=" + dishType +
                ", ingredients=" + ingredients +
                ", dishPrice=" + getDishPrice() +
                '}';
    }
}
