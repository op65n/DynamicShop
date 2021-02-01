package tech.op65n.dynamicshop.database.tables;

import lombok.Getter;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableTransaction implements DatabaseTable {

    @Override
    public int priority() {
        return 2;
    }

    @Override
    @Language("MariaDB")
    public String createIfNotExists() {
        return "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`transaction` ( " +
                "  `id` INT NOT NULL AUTO_INCREMENT, " +
                "  `item_id` INT NULL, " +
                "  `price_buy` DOUBLE NULL, " +
                "  `price_sell` DOUBLE NULL, " +
                "  `demand` DOUBLE NULL, " +
                "  `stock` INT NULL, " +
                "  PRIMARY KEY (`id`)) " +
                "ENGINE = InnoDB;";
    }

    @Getter
    public static class Holder {

        private final int id;
        private final int item_id;
        private final Double priceBuy;
        private final Double priceSell;
        private final double demand;
        private final int stock;

        public Holder(ResultSet set) throws SQLException {
            this.id = set.getInt("id");
            this.item_id = set.getInt("item_id");
            this.priceBuy = set.getDouble("price_buy");
            this.priceSell = set.getDouble("price_sell");
            this.demand = set.getDouble("demand");
            this.stock = set.getInt("stock");
        }

    }

}
