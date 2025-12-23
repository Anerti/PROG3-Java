package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataRetriever {
    private final DBConnection dbConn =  new DBConnection();

    public Dish findDishById(Integer id) throws SQLException {
        final String query =
                """
                SELECT
                Dish.id,
                Dish.name,
                Dish.dish_type,
                Ingredient.id AS ingredient_id,
                Ingredient.name AS ingredient_name,
                Ingredient.price AS ingredient_price,
                Ingredient.category AS ingredient_category
                FROM mini_dish_management_app.Dish
                LEFT JOIN mini_dish_management_app.Ingredient ON Dish.id = Ingredient.id_Dish WHERE Dish.id = ?
                """;
        try(Connection c = dbConn.getConnection()){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Dish dish = null;

            while (rs.next()) {
                if (dish == null)
                    dish = new Dish(
                        rs.getInt("id"),
                        rs.getString("name"),
                        DishTypeEnum.valueOf(rs.getString("dish_type"))
                    );

                int ingredientId = rs.getInt("ingredient_id");
                if (!rs.wasNull()) {
                    Ingredients ingredient = new Ingredients(
                            ingredientId,
                            rs.getString("ingredient_name"),
                            rs.getDouble("ingredient_price"),
                            CategoryEnum.valueOf(rs.getString("ingredient_category"))
                    );
                    dish.addIngredient(ingredient);
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

    public List<Ingredients> findIngredients(int page, int size) throws SQLException {
        final String query =
                """
                    SELECT
                    Ingredient.id AS ingredient_id,
                    Ingredient.name AS ingredient_name,
                    Ingredient.price AS ingredient_price,
                    Ingredient.category AS ingredient_category,
                    Ingredient.id_dish,
                    Dish.name AS dish_name,
                    Dish.dish_type
                    FROM mini_dish_management_app.Ingredient
                    INNER JOIN  mini_dish_management_app.Dish
                    ON Ingredient.id_dish = Dish.id
                    OFFSET ? LIMIT ?;
                """;
        List<Ingredients> results = new ArrayList<>();

        try (Connection c = dbConn.getConnection()) {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, page);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingredients ingredient = new Ingredients(
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getDouble("ingredient_price"),
                        CategoryEnum.valueOf(rs.getString("ingredient_category"))
                );
                String dishName = rs.getString("dish_name");
                if (!rs.wasNull()) {
                    Dish dish = new Dish(
                            rs.getInt("id_dish"),
                            dishName,
                            DishTypeEnum.valueOf(rs.getString("dish_type"))
                    );
                    ingredient.setDish(dish);
                }
                results.add(ingredient);
            }
            rs.close();
            ps.close();
            c.close();
            return results;
        }
        catch(SQLException e){
            throw new SQLException(e);
        }
    }

    private List<String> getAllElementName(String query) throws SQLException {
        List<String> output = new ArrayList<>();
        try (Connection c = dbConn.getConnection()){
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String ingredientName = rs.getString("name");
                output.add(ingredientName.toLowerCase());
            }
            ps.close();
            rs.close();
            c.close();
            return output;
        }
        catch(SQLException e){
            throw new SQLException(e);
        }

    }

    private List<String> getAllIngredientsName() throws SQLException {
        final String query =
                """
                    SELECT
                    Ingredient.name AS name
                    FROM mini_dish_management_app.Ingredient;
                """;

        return getAllElementName(query);
    }

    public List<Ingredients> createIngredients(List<Ingredients> newIngredients) throws SQLException {
        if (newIngredients == null || newIngredients.isEmpty())
            throw new IllegalArgumentException("New ingredients must not be empty");


        Set<String> savedIngredients = new HashSet<>(getAllIngredientsName());
        for (Ingredients ingredient : newIngredients) {
            if (savedIngredients.contains(ingredient.getName().toLowerCase()))
                throw new IllegalArgumentException(String.format("ingredient %s already exist", ingredient.getName()));
        }

        for (Ingredients ingredient : newIngredients) {
            final String insertQuery =
                    """
                        INSERT INTO
                        mini_dish_management_app.Ingredient(id, name, price, category)
                        VALUES (?, ?, ?, ?::mini_dish_management_app.ingredient_category);
                    """;

            try (Connection c = dbConn.getConnection()) {
                PreparedStatement insertPs = c.prepareStatement(insertQuery);
                insertPs.setInt(1, ingredient.getId());
                insertPs.setString(2, ingredient.getName());
                insertPs.setDouble(3, ingredient.getPrice());
                insertPs.setString(4, ingredient.getCategory().toString());
                insertPs.executeUpdate();
                insertPs.close();
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return newIngredients;
    }

    private List<String> getAllDishNames() throws SQLException {
        final String query =
                """
                    SELECT
                    Dish.name AS name
                    FROM mini_dish_management_app.Dish;
                """;
        return getAllElementName(query);
    }
    public Dish saveDish(Dish dishToSave) throws SQLException {
        Set<String> savedDish = new HashSet<String>(getAllDishNames());

        if (savedDish.stream().anyMatch(dish -> dish.equals(dishToSave.getName().toLowerCase()))){
            try (Connection c = dbConn.getConnection()) {
                final String query =
                        """
                                UPDATE mini_dish_management_app.Dish SET dish_type = ?::mini_dish_management_app.dish_type, id = ? WHERE name = ?;
                        """;
                PreparedStatement updatePs = c.prepareStatement(query);
                updatePs.setString(1, dishToSave.getDishType().toString());
                updatePs.setInt(2, dishToSave.getId());
                updatePs.setString(3, dishToSave.getName());
                updatePs.executeUpdate();
                updatePs.close();
            }
            catch (SQLException e){
                throw new SQLException(e);
            }
        }
        try (Connection c = dbConn.getConnection()) {
            final String query =
                    """
                        INSERT INTO mini_dish_management_app.Dish(id, name, dish_type)
                        VALUES  (?, ?, ?::mini_dish_management_app.dish_type);
                    """;
            PreparedStatement insertPs = c.prepareStatement(query);
            insertPs.setInt(1, dishToSave.getId());
            insertPs.setString(2, dishToSave.getName());
            insertPs.setString(3, dishToSave.getDishType().toString());
            insertPs.executeUpdate();
            insertPs.close();
        }
        catch(SQLException e){
            throw new SQLException(e);
        }
        return dishToSave;
    }

    public List<Dish> findDishsByIngredientName(String IngredientName){
        throw  new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ingredients> findIngredientsByCriteria(String ingredientName, CategoryEnum category, String dishName, int page, int size){
        throw  new UnsupportedOperationException("Not supported yet.");
    }
}
