package org.example;

import org.example.db.DBConnection;
import org.example.model.Dish;
import org.example.model.DishOrder;
import org.example.model.DishTypeEnum;
import org.example.model.Order;
import org.example.services.DataRetriever;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnection connection = new DBConnection();
        DataRetriever dr = new DataRetriever(connection);

        //System.out.println(dr.findDishById(1));
        List<DishOrder> dishOrder = List.of(new DishOrder(1, new Dish(1, "Salade fraîche", DishTypeEnum.STARTER, 3500.00), 2), new DishOrder(2, new Dish(2, "Poulet grîllé", DishTypeEnum.MAIN, 3500.00), 2));
        Order order = new Order(1, "ORD00001", Instant.now());
        order.setDishOrder(dishOrder);
        System.out.println(dr.saveOrder(order));
        //System.out.println(dr.findOrderByReference("001"));
        //System.out.println(dr.findDishIngredients(1, 3));
        //Ingredients ingredient1 = new Ingredients(6, "Haricot vert", 800.00, CategoryEnum.VEGETABLE);
        //Ingredients ingredient2 = new Ingredients(7, "Lentilles", 1000.00, CategoryEnum.VEGETABLE);
        //System.out.println(dr.saveDish(new Dish(7, "Canard laqué", DishTypeEnum.MAIN, 50000.00)));
        //System.out.println(dr.findDishesByIngredientName("sal"));
        //System.out.println(dr.findDishIngredientsByCriteria("Tomate", null, null, 1, 5));
        //System.out.println(dr.findDishsByIngredientName("choco"));
        //System.out.println(dr.attachIngredientsToDish(8, 3, 7, 0.5, UnitType.KG));
        //System.out.println(dr.detachIngredientToDishById(7, 1));
    }
}