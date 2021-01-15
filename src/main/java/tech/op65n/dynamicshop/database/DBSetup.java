package tech.op65n.dynamicshop.database;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import tech.op65n.dynamicshop.engine.structure.ShopCategoryStruct;
import tech.op65n.dynamicshop.utils.ObjectUtils;
import tech.op65n.dynamicshop.utils.ShopUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBSetup {

    private static final String CATEGORY_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`category` ( " +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `filename` VARCHAR(128) NOT NULL, " +
            "  PRIMARY KEY (`id`), " +
            "  UNIQUE INDEX `filename_UNIQUE` (`filename` ASC)) " +
            "ENGINE = InnoDB;";

    private static final String ITEM_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`item` ( " +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `item` VARCHAR(1024) NOT NULL, " +
            "  `price_buy` DOUBLE NULL DEFAULT 0.00, " +
            "  `price_sell` DOUBLE NULL DEFAULT 0.00, " +
            "  `item_buys` INT NULL DEFAULT 0, " +
            "  `item_sells` INT NULL DEFAULT 0, " +
            "  `item_history` JSON NULL, " +
            "  `config_price_buy` DOUBLE NULL DEFAULT -1.00, " +
            "  `config_price_sell` DOUBLE NULL DEFAULT -1.00, " +
            "  `category_id` INT NOT NULL, " +
            "  PRIMARY KEY (`id`, `category_id`), " +
            "  INDEX `fk_item_category1_idx` (`category_id` ASC), " +
            "  CONSTRAINT `fk_item_category1` " +
            "    FOREIGN KEY (`category_id`) " +
            "    REFERENCES `dynamic_shop`.`category` (`id`) " +
            "    ON DELETE NO ACTION " +
            "    ON UPDATE NO ACTION) " +
            "ENGINE = InnoDB;";

    public static boolean createTables() {
        try {
            var connection = DataSource.connection();

            var cTable = connection.prepareStatement(CATEGORY_TABLE);
            cTable.executeUpdate();
            cTable.close();
            var iTable = connection.prepareStatement(ITEM_TABLE);
            iTable.executeUpdate();
            iTable.close();

            connection.close();
            Core.gCore().getEngine()._LCACHE().getStartupInfo().setDbReady(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
