package org.example.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
