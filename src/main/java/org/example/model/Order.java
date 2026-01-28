package org.example.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private final int id;
    private final String reference;
    private final Instant creationDateTime;
    private final List<DishOrder> dishOrders;

    public Order(int id, String reference, Instant creationDateTime){
        this.id = id;
        this.reference = reference;
        this.creationDateTime = creationDateTime;
        this.dishOrders = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public Double getTotalAmountWithoutVAT(){
        Objects.requireNonNull(dishOrders, "dishOrders list is null");

        double total = 0.0;
        for (DishOrder dishOrder : dishOrders) {
            Dish dish = dishOrder.getDish();
            if (dish == null) throw new IllegalStateException("DishOrder contains a null Dish");

            Double price = dish.getSellingPrice();
            if (price == null) throw new IllegalStateException("Dish '" + dish.getName() + "' does not have a selling price set");

            int qty = Objects.requireNonNull(dishOrder.getQuantity(), "DishOrder quantity is null");
            if (qty < 0) throw new IllegalArgumentException("Quantity cannot be negative for dish '" + dish.getName() + "'");

            total += price * qty;
        }
        return total;
    }

    public Double getTotalAmountWithVAT(double VAT_RATE){
        return getTotalAmountWithoutVAT() * (1.0 + VAT_RATE);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", dishOrders=" + dishOrders +
                '}';
    }
}
