package tech.op65n.dynamicshop.database;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.settings.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DataSource {

    private static HikariDataSource hds;

    public static String database;

    private DataSource() {
    }

    public static Connection connection() throws SQLException {
        return hds.getConnection();
    }

    public static boolean setup(Configuration.DB dbc) {
        try {
            Properties props = new Properties();
            if (dbc.properties != null && !dbc.properties.isEmpty()) {
                dbc.properties.forEach((key, value) -> {
                    Core.devLogger.log("dataSource." + key + " = " + value);
                    props.setProperty("dataSource." + key, value);
                });
            }
            props.put("dataSource.logWriter", new PrintWriter(System.out));

            HikariConfig config = new HikariConfig(props);

            config.setPoolName("DynShopPool");
            config.setJdbcUrl("jdbc:" + dbc.jdbc + "://" + dbc.ip + ":" + dbc.port + "/" + dbc.database);
            config.setDriverClassName(dbc.driver);
            config.setUsername(dbc.username);
            config.setPassword(dbc.passwd);

            hds = new HikariDataSource(config);

            database = dbc.database;
            return true;
        } catch (Exception e) {
            Core.engineLogger.logSevere("There was an error with database configuration, please fix the error");
            e.printStackTrace();
            Core.pluginLogger.logSevere("Terminating due to unexpected exception!");
            return false;
        }
    }

}
