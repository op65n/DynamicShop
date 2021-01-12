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
            "  `filename` VARCHAR(64) NOT NULL, " +
            "  `name` VARCHAR(64) NOT NULL, " +
            "  `priority` INT NULL DEFAULT 1000, " +
            "  `icon_type` INT NULL DEFAULT 0, " +
            "  `icon_item` VARCHAR(1024) NULL, " +
            "  `icon_display` VARCHAR(45) NULL, " +
            "  `icon_lore` VARCHAR(1024) NULL, " +
            "  PRIMARY KEY (`id`), " +
            "  UNIQUE INDEX `name_UNIQUE` (`name` ASC)) " +
            "ENGINE = InnoDB;";

    private static final String ITEM_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`item` ( " +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `item_type` INT NULL DEFAULT 0, " +
            "  `item_material` VARCHAR(1024) NOT NULL, " +
            "  `price_buy` DOUBLE NULL DEFAULT 0.00, " +
            "  `price_sell` DOUBLE NULL DEFAULT 0.00, " +
            "  `buys` INT NOT NULL DEFAULT 0, " +
            "  `sells` INT NOT NULL DEFAULT 0, " +
            "  `category_id` INT NOT NULL, " +
            "  PRIMARY KEY (`id`, `category_id`), " +
            "  INDEX `fk_item_category_idx` (`category_id` ASC), " +
            "  CONSTRAINT `fk_item_category` " +
            "    FOREIGN KEY (`category_id`) " +
            "    REFERENCES `dynamic_shop`.`category` (`id`) " +
            "    ON DELETE NO ACTION " +
            "    ON UPDATE NO ACTION) " +
            "ENGINE = InnoDB;";

    private static final String ITEM_META_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`item_meta` ( " +
            "  `id` INT NOT NULL, " +
            "  `display` VARCHAR(64) NULL, " +
            "  `lore` VARCHAR(1024) NULL DEFAULT NULL, " +
            "  `priority` INT NULL DEFAULT 1000, " +
            "  `flatline` TINYINT NULL DEFAULT 0, " +
            "  `command` VARCHAR(256) NULL DEFAULT NULL, " +
            "  `item_id` INT NOT NULL, " +
            "  `item_category_id` INT NOT NULL, " +
            "  PRIMARY KEY (`id`), " +
            "  INDEX `fk_item_meta_item1_idx` (`item_id` ASC, `item_category_id` ASC), " +
            "  CONSTRAINT `fk_item_meta_item1` " +
            "    FOREIGN KEY (`item_id` , `item_category_id`) " +
            "    REFERENCES `dynamic_shop`.`item` (`id` , `category_id`) " +
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
            var imTable = connection.prepareStatement(ITEM_META_TABLE);
            imTable.executeUpdate();
            imTable.close();

            connection.close();
            Core.gCore().getEngine()._LCACHE().getStartupInfo().setDbReady(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
