package org.example;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataRetriever dr = new DataRetriever();
        Ingredients ingredient1 = new Ingredients(6, "Haricot vert", 800.00, CategoryEnum.VEGETABLE);
        System.out.println(dr.createIngredients(List.of(ingredient1)));
    }
}