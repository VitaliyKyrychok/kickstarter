package ua.com.goit.kyrychok.dao.database.datasource_provider;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class H2DataSourceProvider implements DbDataSourceProvider {
    private JdbcDataSource dataSource;
    private String url;
    private String userName;
    private String userPassword;

    public void setUrl(String url) {
        //TODO Configure this
        this.url = "jdbc:h2:file:G:\\Software\\apache-tomcat-8.0.24\\webapps\\xxx\\WEB-INF\\classes\\database\\kickstarter.db;IFEXISTS=TRUE";
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection result = dataSource.getConnection();
        result.setAutoCommit(false);
        return result;
    }

    @Override
    public void testConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void init(String url, String userName, String userPassword) throws SQLException {
        System.out.println("url=" + url);
        dataSource = new JdbcDataSource();
        dataSource.setURL(url);
        dataSource.setUser(userName);
        dataSource.setPassword(userPassword);
    }

    public void init() throws SQLException {
        dataSource = new JdbcDataSource();
        dataSource.setURL(url);
        dataSource.setUser(userName);
        dataSource.setPassword(userPassword);
        System.out.println("url=" + dataSource.getUrl());
        testConnection();
    }
}
