package ua.com.goit.kyrychok.dao.database.datasource_provider;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbDataSourceProvider {

    Connection getConnection() throws SQLException;

}
