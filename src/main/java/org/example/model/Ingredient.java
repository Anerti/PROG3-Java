package org.example.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ingredient {

    private final int id;
    private final String name;
    private final Double price;
    private final CategoryEnum category;
    private final List<StockMovement> stockMovementList = new ArrayList<>();
    public Ingredient(int id, String name, Double price, CategoryEnum category) {
        this.id = id;
        this.name = name;
        this.price = price != null ? price : 0.0;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void addStockMovement(StockMovement newStockMovement){
        this.stockMovementList.add(newStockMovement);
    }

    public List<StockMovement> getStockMovementList() {
        return stockMovementList;
    }

    public StockValue getStockValueAt(Instant date) {
        if (stockMovementList.isEmpty()) {
            throw new IllegalArgumentException("Ingredient has no stock movements");
        }

        UnitType unit = stockMovementList.getFirst().getValue().getUnit();

        double total = 0.0;
        for (StockMovement mv : stockMovementList) {
            if (!mv.getCreationDatetime().isAfter(date)) {
                double qty = mv.getValue().getQuantity();
                total += (mv.getType() == MovementTypeEnum.IN) ? qty : -qty;
            }
        }

        return new StockValue(total, unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", stockMovementList=" + stockMovementList +
                '}';
    }
}
