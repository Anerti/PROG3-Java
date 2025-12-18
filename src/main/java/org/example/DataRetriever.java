package org.example;

import java.util.List;

public class DataRetriever {
    private final DBConnection connection =  new DBConnection();

    public Dish findDishById(Integer id){
        throw  new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ingredients> findIngredients(int page, int size){
        throw  new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ingredients> createIngredients(List<Ingredients> newIngredients){
        throw  new UnsupportedOperationException("Not supported yet.");
    }

    public Dish saveDish(Dish dishToSave){
        throw  new UnsupportedOperationException("Not supported yet.");
    }

    public List<Dish> findDishsByIngredientName(String IngredientName){
        throw  new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ingredients> findIngredientsByCriteria(String ingredientName, CategoryEnum category, String dishName, int page, int size){
        throw  new UnsupportedOperationException("Not supported yet.");
    }
}
