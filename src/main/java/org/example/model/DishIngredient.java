package org.example.model;

import java.util.Objects;

public class DishIngredient {
    private final int id;
    private final Ingredient ingredient;
    private final Dish dish;
    private final Double quantity;
    private final UnitType unit;

    public DishIngredient(int id, Ingredient ingredient, Dish dish, Double quantity, UnitType unit) {
        this.id = id;
        this.ingredient = ingredient;
        this.dish = dish;
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Dish getDish() {
        return dish;
    }

    public Double getQuantity() {
        return quantity;
    }

    public UnitType getUnit() {
        return unit;
    }

    public Double getIngredientPrice(){
        if (quantity == null) return 0.0;
        return ingredient.getPrice() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DishIngredient that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return ingredient +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", dishName=" + dish.getName() +
                '}';
    }
}
