package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {

    private final int id;
    private final String name;
    private final DishTypeEnum dishType;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private Double sellPrice;

    public Dish(int id, String name, DishTypeEnum dishType, Double sellPrice) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.sellPrice = sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getSellPrice(){
        return sellPrice;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredient == null) throw new NullPointerException("ingredient is null");

        if (ingredient.getDish() != null && ingredient.getDish() != this)
            ingredient.getDish().removeIngredient(ingredient);

        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
            ingredient.setDish(this);
        }
    }

    public Double getGrossMargin() {
        if (getSellPrice() == null) throw new IllegalStateException(this.name + " price is is null");
        if (getSellPrice() <= 0) throw new IllegalStateException(this.name + " price cannot be less than 1");
        return getSellPrice() - getDishCost();
    }

    public void removeIngredient(Ingredient ingredient) {
        if (ingredients.remove(ingredient)) ingredient.setDish(null);
    }

    public Double getDishCost() {
        return ingredients.stream()
                .mapToDouble(Ingredient::getPrice)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish dish)) return false;
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
                ", dishPrice=" + getDishCost() +
                ", dishSellCost=" + getGrossMargin() +
                '}';
    }
}
