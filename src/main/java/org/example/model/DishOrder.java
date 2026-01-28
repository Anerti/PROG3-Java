package org.example.model;

public class DishOrder {
    private final int id;
    private final Dish dish;
    private final Integer quantity;
    public DishOrder(int id, Dish dish, Integer quantity){
        this.id = id;
        this.dish = dish;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Dish getDish() {
        return dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                "id=" + id +
                ", dish=" + dish +
                ", quantity=" + quantity +
                '}';
    }
}
