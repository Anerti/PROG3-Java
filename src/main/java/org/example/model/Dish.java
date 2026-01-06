package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {

    private final int id;
    private String name;
    private DishTypeEnum dishType;
    private final List<Ingredients> ingredients = new ArrayList<>();

    public Dish(int id, String name, DishTypeEnum dishType) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
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

    public void addIngredient(Ingredients ingredient) {
        if (ingredient == null) throw new NullPointerException("ingredient is null");

        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
            ingredient.setDish(this);
        }
    }

    public void removeIngredient(Ingredients ingredient) {
        if (ingredients.remove(ingredient)) ingredient.setDish(null);
    }

    public Double getDishPrice() {
        return ingredients.stream()
                .map(Ingredients::getPrice)
                .filter(Objects::nonNull)
                .reduce(0.0, Double::sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        Dish dish = (Dish) o;
        return id == dish.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
