package ua.com.goit.kyrychok.dao.database;

import ua.com.goit.kyrychok.dao.ProjectEventDao;
import ua.com.goit.kyrychok.dao.database.datasource_provider.DbDataSourceProvider;
import ua.com.goit.kyrychok.dao.database.sql_provider.ProjectEventSqlProvider;
import ua.com.goit.kyrychok.domain.ProjectEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbProjectEventDao implements ProjectEventDao {
    private DbDataSourceProvider dataSourceProvider;
    private ProjectEventSqlProvider sqlProvider;

    public DbProjectEventDao(DbDataSourceProvider dbDataSourceProvider, ProjectEventSqlProvider sqlProvider) {
        this.dataSourceProvider = dbDataSourceProvider;
        this.sqlProvider = sqlProvider;
    }

    List<ProjectEvent> fetch(int projectId, Connection connection) throws SQLException {
        List<ProjectEvent> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlProvider.get4Fetch())) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProjectEvent projectEvent = new ProjectEvent(resultSet.getDate("event_date"), resultSet.getString("message"));
                projectEvent.setId(resultSet.getInt("id"));
                result.add(projectEvent);
            }
        }
        return result;
    }

    @Override
    public List<ProjectEvent> fetch(int projectId) {
        List<ProjectEvent> result = new ArrayList<>();
        try (Connection connection = dataSourceProvider.getConnection()) {
            result = fetch(projectId, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
