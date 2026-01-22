package org.example.model;

public class DishIngredient {
    private final int id;
    private final Ingredient ingredient;
    private final Dish dish;
    private Double quantity;
    private UnitType unit;

    public DishIngredient(int id, Ingredient ingredient, Dish dish) {
        this.id = id;
        this.ingredient = ingredient;
        this.dish = dish;
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
        return ingredient.getPrice();
    }

}
