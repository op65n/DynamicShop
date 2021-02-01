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
    private ShopConfiguration shop;

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
        public int default_stock = 300;
        public double stock_curve = 1.03;
        public double no_stock_curve = 1.2;
        public double golden_demand = 0.6;
        public int history_length = 100;
    }

}
