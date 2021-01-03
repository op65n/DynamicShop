package com.sebbaindustries.dynamicshop.database;

import com.sebbaindustries.dynamicshop.settings.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource hds;

    private DataSource() {}

    public static Connection connection() throws SQLException {
        return hds.getConnection();
    }

    public static void setup(Configuration.DB dbc) {
        config.setJdbcUrl("jdbc:" + dbc.jdbc + "://" + dbc.ip + ":" + dbc.port + "/" + dbc.database);
        config.setDriverClassName(dbc.driver);
        config.setUsername(dbc.username);
        config.setPassword(dbc.passwd);

        if (dbc.properties != null && !dbc.properties.isEmpty())
            dbc.properties.forEach((key, value) -> config.addDataSourceProperty("dataSource." + key, value));

        hds = new HikariDataSource(config);
    }
}
