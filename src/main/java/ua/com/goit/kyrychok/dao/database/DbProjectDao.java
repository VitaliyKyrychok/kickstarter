package ua.com.goit.kyrychok.dao.database;

import ua.com.goit.kyrychok.dao.ProjectDao;
import ua.com.goit.kyrychok.dao.database.datasource_provider.DbDataSourceProvider;
import ua.com.goit.kyrychok.dao.database.sql_provider.ProjectSqlProvider;
import ua.com.goit.kyrychok.domain.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbProjectDao implements ProjectDao {
    private DbDataSourceProvider dbDataSourceProvider;
    private ProjectSqlProvider sqlProvider;

    public DbProjectDao(DbDataSourceProvider dbDataSourceProvider, ProjectSqlProvider sqlProvider) {
        this.dbDataSourceProvider = dbDataSourceProvider;
        this.sqlProvider = sqlProvider;
    }

    @Override
    public Project get(int id) {
        Project result = null;
        try (Connection connection = dbDataSourceProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sqlProvider.get4Load())) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    result = new Project(resultSet.getString("name"), resultSet.getInt("goal"), resultSet.getDate("deadline_date"));
                    result.setShortDescription(resultSet.getString("description"));
                    result.setBalance(resultSet.getInt("balance"));
                    result.setDemoLink(resultSet.getString("demo_link"));
                    result.setCreateDate(resultSet.getDate("create_date"));
                    result.setId(id);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void setBalance(int projectId, int amount) {
        try (Connection connection = dbDataSourceProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlProvider.get4SetBalance())) {
            statement.setInt(1, amount);
            statement.setInt(2, projectId);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getBalance(int projectId) {
        int result = 0;
        try (Connection connection = dbDataSourceProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlProvider.get4GetBalance())) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("balance");
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Project> fetch(int categoryId) {
        List<Project> result = new ArrayList<>();
        try (Connection connection = dbDataSourceProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlProvider.get4Fetch())) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project(resultSet.getString("project_name"), resultSet.getInt("goal"), resultSet.getDate("deadline_date"));
                project.setShortDescription(resultSet.getString("short_description"));
                project.setId(resultSet.getInt("id"));
                project.setBalance(resultSet.getInt("balance"));
                result.add(project);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Project> fetchAll() {
        List<Project> result = new ArrayList<>();
        try (Connection connection = dbDataSourceProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlProvider.get4FetchAll())) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project(resultSet.getString("project_name"), resultSet.getInt("goal"), resultSet.getDate("deadline_date"));
                project.setShortDescription(resultSet.getString("short_description"));
                project.setId(resultSet.getInt("id"));
                project.setBalance(resultSet.getInt("balance"));
                result.add(project);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
