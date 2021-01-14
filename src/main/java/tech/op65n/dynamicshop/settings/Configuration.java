package tech.op65n.dynamicshop.settings;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
@Getter
@NoArgsConstructor
public class Configuration {

    private DB database;

    public static class DB {
        public String ip;
        public String port;
        public String driver;
        public String jdbc;
        public String database;
        public String username;
        public String passwd;
        public HashMap<String, String> properties = new HashMap<>();
    }

    public static class ShopConfiguration {
        public Integer default_stock = 300;
        public Double stock_curve = 1.03;
        public Double no_stock_curve = 1.2;
        public Double golden_demand = 0.6;
        public Integer history_length = 100;
    }

}
