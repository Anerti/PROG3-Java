package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {

    private final int id;
    private final String name;
    private final DishTypeEnum dishType;
    private final List<DishIngredient> dishIngredients = new ArrayList<>();
    private Double price;

    public Dish(int id, String name, DishTypeEnum dishType, Double price) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.price = price;
    }

    public void setSellingPrice(Double sellPrice) {
        this.price = sellPrice;
    }

    public Double getSellingPrice(){
        return price;
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

    public List<DishIngredient> getDishIngredients() {
        return dishIngredients;
    }

    public void addDishIngredient(DishIngredient dishIngredient) {
        if (dishIngredient == null) throw new NullPointerException("ingredient is null");

        if (!dishIngredients.contains(dishIngredient)) {
            dishIngredients.add(dishIngredient);
        }
    }

    public Double getGrossMargin() {
        if (getSellingPrice() == null) throw new IllegalStateException(this.name + " price is is null");
        if (getSellingPrice() <= 0) throw new IllegalStateException(this.name + " price cannot be less than 1");
        return getSellingPrice() - getDishCost();
    }

    public Double getDishCost() {
        return dishIngredients.stream()
                .mapToDouble(DishIngredient::getIngredientPrice)
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
                ", ingredients=" + dishIngredients +
                ", dishPrice=" + getDishCost() +
                ", grossMargin=" + getGrossMargin() +
                '}';
    }
}
