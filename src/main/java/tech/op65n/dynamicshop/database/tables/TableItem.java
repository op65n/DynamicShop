package tech.op65n.dynamicshop.database.tables;

import lombok.Getter;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableItem implements DatabaseTable {

    @Override
    public int priority() {
        return 1;
    }

    @Override
    @Language("MariaDB")
    public String createIfNotExists() {
        return "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`item` ( " +
                "  `id` INT NOT NULL AUTO_INCREMENT, " +
                "  `item` VARCHAR(1024) NOT NULL, " +
                "  `price_buy` DOUBLE UNSIGNED NULL, " +
                "  `price_sell` DOUBLE UNSIGNED NULL, " +
                "  `item_buys` INT NULL DEFAULT 0, " +
                "  `item_sells` INT NULL DEFAULT 0, " +
                "  `item_stock` INT NULL DEFAULT 0, " +
                "  `config_price_buy` DOUBLE UNSIGNED NULL, " +
                "  `config_price_sell` DOUBLE UNSIGNED NULL, " +
                "  `category_id` INT NOT NULL, " +
                "  PRIMARY KEY (`id`, `category_id`), " +
                "  INDEX `fk_item_category1_idx` (`category_id` ASC), " +
                "  CONSTRAINT `fk_item_category1` " +
                "    FOREIGN KEY (`category_id`) " +
                "    REFERENCES `dynamic_shop`.`category` (`id`) " +
                "    ON DELETE NO ACTION " +
                "    ON UPDATE NO ACTION) " +
                "ENGINE = InnoDB;";
    }

    @Getter
    public static class Holder {

        private final int id;
        private final String item;
        private final Double priceBuy;
        private final Double priceSell;
        private final int buys;
        private final int sells;
        private final int stock;
        private final Double cnfPriceBuy;
        private final Double cnfPriceSell;
        private final int categoryID;

        public Holder(ResultSet set) throws SQLException {
            this.id = set.getInt("id");
            this.item = set.getString("item");
            this.priceBuy = set.getDouble("price_buy");
            this.priceSell = set.getDouble("price_sell");
            this.buys = set.getInt("item_buys");
            this.sells = set.getInt("item_sells");
            this.stock = set.getInt("item_stock");
            this.cnfPriceBuy = set.getDouble("config_price_buy");
            this.cnfPriceSell = set.getDouble("config_price_sell");
            this.categoryID = set.getInt("category_id");
        }

    }

}
