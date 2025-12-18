package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private final DBConnection dbConn =  new DBConnection();

    public Dish findDishById(Integer id) throws SQLException {
        final String query = """
                SELECT\s
                Dish.id, Dish.name, Dish.dish_type, Ingredient.id AS ingredient_id, Ingredient.name AS ingredient_name, Ingredient.price, Ingredient.category\s
                FROM mini_dish_management_app.Dish\s
                LEFT JOIN mini_dish_management_app.Ingredient ON Dish.id = Ingredient.id_Dish WHERE Dish.id = ?
               \s
               """;
        try(Connection c = dbConn.getConnection()){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Dish dish = null;
            List<Ingredients> ingredients = new ArrayList<>();

            while (rs.next()) {
                if (dish == null) dish = new Dish(
                        rs.getInt("id"),
                        rs.getString("name"),
                        DishTypeEnum.valueOf(rs.getString("dish_type")),
                        ingredients
                );

                int ingId = rs.getInt("ingredient_id");
                if (!rs.wasNull()) {
                    Ingredients ing = new Ingredients(
                            ingId,
                            rs.getString("ingredient_name"),
                            rs.getDouble("price"),
                            CategoryEnum.valueOf(rs.getString("category"))
                    );
                    ingredients.add(ing);
                }
            }
            rs.close();
            ps.close();
            c.close();
            return dish;
        }
        catch(SQLException e){
            throw new SQLException(e);
        }
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
