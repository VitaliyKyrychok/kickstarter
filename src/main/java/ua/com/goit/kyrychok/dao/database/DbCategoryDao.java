package ua.com.goit.kyrychok.dao.database;

import ua.com.goit.kyrychok.dao.CategoryDao;
import ua.com.goit.kyrychok.dao.database.datasource_provider.DbDataSourceProvider;
import ua.com.goit.kyrychok.dao.database.sql_provider.CategorySqlProvider;
import ua.com.goit.kyrychok.domain.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCategoryDao implements CategoryDao {
    private DbDataSourceProvider dbDataSourceProvider;
    private DbProjectDao dbProjectDao;
    private CategorySqlProvider sqlProvider;

    public DbCategoryDao(DbDataSourceProvider dbDataSourceProvider, DbProjectDao dbProjectDao, CategorySqlProvider sqlProvider) {
        this.dbDataSourceProvider = dbDataSourceProvider;
        this.dbProjectDao = dbProjectDao;
        this.sqlProvider = sqlProvider;
    }

    @Override
    public String getWelcomeMessage() {
        String result = "";
        try (Connection connection = dbDataSourceProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlProvider.get4GetWelcomeMessage())) {
            if (resultSet.next()) {
                result = resultSet.getString("message");
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Category> fetch() {
        List<Category> result = new ArrayList<>();
        try (Connection connection = dbDataSourceProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlProvider.get4Fetch())) {
            while (resultSet.next()) {
                Category category = new Category(resultSet.getString("name"));
                category.setId(resultSet.getInt("id"));
                result.add(category);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Category get(int id, Connection connection) throws SQLException {
        Category result = null;
        try (PreparedStatement statement = connection.prepareStatement(sqlProvider.get4Get())) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new Category(resultSet.getString("category_name"));
                result.setId(id);
            }
        }
        return result;
    }

    @Override
    public Category get(int id) {
        Category result = null;
        try (Connection connection = dbDataSourceProvider.getConnection()) {
            result = get(id, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Category getByProjectId(int id) {
        Category result = null;
        try (Connection connection = dbDataSourceProvider.getConnection()) {
            int categoryId = 0;
            try (PreparedStatement statement = connection.prepareStatement(sqlProvider.get4GetByProjectId())) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    categoryId = resultSet.getInt("category_id");
                }
            }
            result = get(categoryId, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
