package org.example;

import org.example.db.DBConnection;
import org.example.services.DataRetriever;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnection connection = new DBConnection();
        DataRetriever dr = new DataRetriever(connection);

        //System.out.println(dr.findDishById(1));
        //System.out.println(dr.findIngredients(1, 3));
        /*Ingredients ingredient1 = new Ingredients(6, "Haricot vert", 800.00, CategoryEnum.VEGETABLE);
        Ingredients ingredient2 = new Ingredients(7, "Lentilles", 1000.00, CategoryEnum.VEGETABLE);*/
        //System.out.println(dr.saveDish(new Dish(7, "Pizza", DishTypeEnum.STARTER)));
        //System.out.println(dr.findDishsByIngredientName("Chocolat"));
        //System.out.println(dr.findIngredientsByCriteria("Tomate", null, null, 1, 5));
        System.out.println(dr.findDishsByIngredientName("choco"));
    }
}