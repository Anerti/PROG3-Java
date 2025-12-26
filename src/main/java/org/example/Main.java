package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataRetriever dr = new DataRetriever();
        /*Ingredients ingredient1 = new Ingredients(6, "Haricot vert", 800.00, CategoryEnum.VEGETABLE);
        Ingredients ingredient2 = new Ingredients(7, "Lentilles", 1000.00, CategoryEnum.VEGETABLE);*/
        System.out.println(dr.saveDish(new Dish(7, "Pizza", DishTypeEnum.STARTER)));
    }
}