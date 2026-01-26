package org.example.model;

public class StockValue {
    private final double quantity;
    private final UnitType unit;

    public StockValue(double quantity, UnitType unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public UnitType getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "StockValue{" +
                "quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}
