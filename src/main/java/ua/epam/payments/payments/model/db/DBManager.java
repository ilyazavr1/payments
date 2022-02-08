package ua.epam.payments.payments.model.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

    private static DBManager instance;
    private final HikariDataSource ds;


    private DBManager() {
        Properties properties = getProps();
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(properties.getProperty("driver"));
        config.setJdbcUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("user"));
        config.setPassword(properties.getProperty("password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);

    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private Properties getProps()  {
        Properties properties = new Properties();

        try {
            properties.load(DBManager.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
