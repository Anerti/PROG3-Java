package org.example.services;

import org.example.db.DBConnection;
import org.example.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataRetriever {
    private final DBConnection dbConn;

    public DataRetriever(DBConnection dbConn){
        this.dbConn = dbConn;
    }

    private Dish dishRetriever(ResultSet rs) throws SQLException {
        Dish dish = null;

        while (rs.next()) {
            if (dish == null) {
                dish = new Dish(
                        rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        DishTypeEnum.valueOf(rs.getString("dish_type")),
                        rs.getDouble("dish_price")
                );
                if (rs.wasNull()) dish.setSellingPrice(null);
            }

            int ingredientId = rs.getInt("ingredient_id");
            if (!rs.wasNull()) {
                Ingredient ingredient = new Ingredient(
                        ingredientId,
                        rs.getString("ingredient_name"),
                        rs.getDouble("ingredient_price"),
                        CategoryEnum.valueOf(rs.getString("ingredient_category"))
                );
                dish.addDishIngredient(
                        new DishIngredient(
                                rs.getInt("dishingredient_id"),
                                ingredient,
                                dish,
                                rs.getDouble("quantity"),
                                UnitType.valueOf(rs.getString("unit"))
                        )
                );
            }
        }
        return dish;
    }

    public Dish findDishById(Integer id) throws SQLException {
        final String query =
                """
                SELECT
                Dish.id AS dish_id,
                Dish.name AS dish_name,
                Dish.dish_type,
                Dish.selling_price AS dish_price,
                Ingredient.id AS ingredient_id,
                Ingredient.name AS ingredient_name,
                Ingredient.price AS ingredient_price,
                Ingredient.category AS ingredient_category,
                dishingredient.id AS dishingredient_id,
                dishingredient.quantity_required AS quantity,
                dishingredient.unit AS unit
                FROM
                    mini_dish_management_app.dishingredient
                INNER JOIN
                    mini_dish_management_app.Ingredient
                ON
                    dishingredient.id_ingredient = Ingredient.id
                INNER JOIN
                    mini_dish_management_app.Dish
                ON
                    dishingredient.id_dish = Dish.id
                WHERE
                    dish.id = ?;
                """;
        try(
                Connection c = dbConn.getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                return dishRetriever(rs);
            }
        }
        catch(SQLException e){
            throw new SQLException(e);
        }
    }

    private List<DishIngredient> ingredientRetriever(ResultSet rs) throws SQLException {
        List<DishIngredient> results = new ArrayList<>();
        while (rs.next()) {
            DishIngredient dishINgredient = new DishIngredient(
                    rs.getInt("ingredient_id"),
                    new Ingredient(
                            rs.getInt("ingredient_id"),
                            rs.getString("ingredient_name"),
                            rs.getDouble("ingredient_price"),
                            CategoryEnum.valueOf(rs.getString("ingredient_category"))
                    ),
                    new Dish(
                            rs.getInt("dish_id"),
                            rs.getString("dish_name"),
                            DishTypeEnum.valueOf(rs.getString("dish_type")),
                            rs.getDouble("dish_price")
                    ),
                    rs.getDouble("quantity"),
                    UnitType.valueOf(rs.getString("unit"))
            );
            results.add(dishINgredient);
        }
        return results;
    }

    public List<DishIngredient> findDishIngredients(int page, int size) throws SQLException {
        final String query =
                """
                SELECT
                Dish.id AS dish_id,
                Dish.name AS dish_name,
                Dish.dish_type,
                Dish.selling_price AS dish_price,
                Ingredient.id AS ingredient_id,
                Ingredient.name AS ingredient_name,
                Ingredient.price AS ingredient_price,
                Ingredient.category AS ingredient_category,
                dishingredient.id AS dishingredient_id,
                dishingredient.quantity_required AS quantity,
                dishingredient.unit AS unit
                FROM
                    mini_dish_management_app.dishingredient
                INNER JOIN
                    mini_dish_management_app.Ingredient
                ON
                    dishingredient.id_ingredient = Ingredient.id
                INNER JOIN
                    mini_dish_management_app.Dish
                ON
                    dishingredient.id_dish = Dish.id
                LIMIT ?
                OFFSET ?;
                """;

        try (
                Connection c = dbConn.getConnection();
                PreparedStatement ps = c.prepareStatement(query);
        ) {
            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            try(ResultSet rs = ps.executeQuery()) {
                return ingredientRetriever(rs);
            }
        }
        catch(SQLException e){
            throw new SQLException(e);
        }
    }

    private List<String> getAllElementName(String query) throws SQLException {
        List<String> output = new ArrayList<>();
        try (
                Connection c = dbConn.getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ){
            try(ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String ingredientName = rs.getString("name");
                    output.add(ingredientName.toLowerCase());
                }
            }
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

    /*createIngredients Not tested*/
    public List<Ingredient> createIngredients(List<Ingredient> newIngredients) throws SQLException {
        if (newIngredients == null || newIngredients.isEmpty())
            throw new IllegalArgumentException("newIngredients must not be empty");

        Set<String> savedIngredients = new HashSet<>(getAllIngredientsName());
        for (Ingredient ingredient : newIngredients) {
            if (savedIngredients.contains(ingredient.getName().toLowerCase()))
                throw new IllegalArgumentException(String.format("Ingredient %s already exist", ingredient.getName()));
        }

        for (Ingredient ingredient : newIngredients) {
            final String insertQuery =
                    """
                        INSERT INTO
                        mini_dish_management_app.Ingredient(id, name, price, category)
                        VALUES (?, ?, ?, ?::mini_dish_management_app.ingredient_category);
                    """;

            try (
                    Connection c = dbConn.getConnection();
                    PreparedStatement insertPs = c.prepareStatement(insertQuery)
            ) {
                insertPs.setInt(1, ingredient.getId());
                insertPs.setString(2, ingredient.getName());
                insertPs.setDouble(3, ingredient.getPrice());
                insertPs.setString(4, ingredient.getCategory().toString());
                insertPs.executeUpdate();
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return newIngredients;
    }


    public String attachIngredientsToDish(int id, int ingredientId, int dishId, double quantity, UnitType unit) throws SQLException {
        final String query =
                """
                    INSERT INTO mini_dish_management_app.dishingredient (id, id_dish, id_ingredient, quantity_required, unit)
                    VALUES (?, ?, ?, ?, ?::mini_dish_management_app.unit_type);
                """;
        try (
                Connection c = dbConn.getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ){
          ps.setInt(1, id);
          ps.setInt(2, dishId);
          ps.setInt(3, ingredientId);
          ps.setDouble(4, quantity);
          ps.setString(5, String.valueOf(unit));
          ps.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException(e);
        }
        return "Ingredient attached to dish";
    }

    public List<Ingredient> detachIngredientsToDish(List<String> ingredientsName){
        throw new RuntimeException("not implemented");
    }

    public Dish saveDish(Dish dish) throws SQLException {
        final String query =
        """
            INSERT INTO mini_dish_management_app.Dish (id, dish_type, selling_price, name)
            VALUES (?, ?::mini_dish_management_app.dish_type, ?, ?)
            ON CONFLICT (id) DO UPDATE
            SET
                dish_type = EXCLUDED.dish_type,
                selling_price = EXCLUDED.selling_price,
                name = EXCLUDED.name;
        """;
        try (
                Connection c = dbConn.getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, dish.getId());
            ps.setString(2, String.valueOf(dish.getDishType()));
            ps.setDouble(3, dish.getSellingPrice());
            ps.setString(4, dish.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return dish;
    }

    public List<Dish> findDishesByIngredientName(String IngredientName) throws SQLException {
        final String query =
                """
                    SELECT
                    dish.id AS dish_id,
                    dish.name AS dish_name,
                    dish.dish_type,
                    dish.selling_price AS dish_price,
                    Ingredient.id AS Ingredient_id,
                    Ingredient.name AS ingredient_name,
                    Ingredient.price AS ingredient_price,
                    Ingredient.category AS ingredient_category,
                    dishingredient.id AS dishingredient_id,
                    dishingredient.quantity_required AS quantity,
                    dishingredient.unit AS unit
                    FROM
                        mini_dish_management_app.dishingredient
                    INNER JOIN
                        mini_dish_management_app.Ingredient
                    ON
                        dishingredient.id_ingredient = Ingredient.id
                    INNER JOIN
                        mini_dish_management_app.dish
                    ON
                        dish.id = dishingredient.id_dish
                    WHERE
                        dish.name ILIKE ?;
                """;

        try(
                Connection c = dbConn.getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ){
            ps.setString(1, String.format("%%%s%%", IngredientName));
            List<Dish> dishesToReturn = new ArrayList<>();

            try(ResultSet rs = ps.executeQuery()) {
                dishesToReturn.add(dishRetriever(rs));
                return dishesToReturn;
            }
        }
        catch(SQLException e){
            throw new SQLException(e);
        }
    }
    public List<DishIngredient> findDishIngredientsByCriteria(String ingredientName, CategoryEnum category, String dishName, int page, int size) throws SQLException {
        StringBuilder query = queryBuilder(ingredientName, category, dishName);

        try (
                Connection c = dbConn.getConnection();
                PreparedStatement ps = c.prepareStatement(String.valueOf(query))
        ) {
            int parameterCount = 1;

            if (ingredientName != null &&  !ingredientName.isBlank()) {
                ps.setString(parameterCount++, String.format("%%%s%%", ingredientName));
            }
            if (category != null) {
                ps.setString(parameterCount++, String.format("%%%s%%", category));
            }
            if (dishName != null && !dishName.isBlank()) {
                ps.setString(parameterCount++, String.format("%%%s%%", dishName));
            }
            if (size < 1) size = 10;
            ps.setInt(parameterCount++, size);

            if (page < 1) page = 1;
            ps.setInt(parameterCount,(page - 1) * size);

            try(ResultSet rs = ps.executeQuery()) {
                return ingredientRetriever(rs);
            }
        }
        catch (SQLException e){
            throw new SQLException(e);
        }
    }


    private static StringBuilder queryBuilder(String ingredientName, CategoryEnum category, String dishName) {
        StringBuilder query = new StringBuilder(
    """
        SELECT
            dish.id AS dish_id,
            dish.name AS dish_name,
            dish.dish_type,
            dish.selling_price AS dish_price,
            Ingredient.id AS Ingredient_id,
            Ingredient.name AS ingredient_name,
            Ingredient.price AS ingredient_price,
            Ingredient.category AS ingredient_category,
            dishingredient.id AS dishingredient_id,
            dishingredient.quantity_required AS quantity,
            dishingredient.unit AS unit
        FROM
            mini_dish_management_app.dishingredient
        INNER JOIN
            mini_dish_management_app.Dish
        ON
            Dish.id = dishingredient.id_dish
        INNER JOIN
            mini_dish_management_app.ingredient
        ON
            ingredient.id = dishingredient.id_ingredient
        WHERE 1=1
    """);

        if (ingredientName != null && !ingredientName.isBlank())
            query.append(" AND Ingredient.name ILIKE ?");

        if (dishName != null && !dishName.isBlank())
            query.append(" AND Dish.name ILIKE ?");

        if (category != null)
            query.append(" AND Ingredient.category = ?");

        query.append(" LIMIT ? OFFSET ?");
        return query;
    }
}
