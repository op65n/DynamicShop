package com.sebbaindustries.dynamicshop.database;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBSetup {

    private static final String CATEGORY_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`category` ( " +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `name` VARCHAR(45) NULL UNIQUE, " +
            "  PRIMARY KEY (`id`)) " +
            "ENGINE = InnoDB;";

    private static final String ITEM_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`item` ( " +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `material` VARCHAR(64) NOT NULL, " +
            "  `buy_price` DOUBLE NULL DEFAULT NULL, " +
            "  `sell_price` DOUBLE NULL DEFAULT NULL, " +
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
            "  `display` VARCHAR(64) NULL DEFAULT '%item%', " +
            "  `lore` VARCHAR(512) NULL DEFAULT NULL, " +
            "  `priority` INT NULL DEFAULT 1000, " +
            "  `flatline` TINYINT NULL DEFAULT 0, " +
            "  `base64` VARCHAR(512) NULL DEFAULT NULL, " +
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

    public static void createTables() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createCategories(List<ShopCategory> categories) {
        categories.forEach(category -> {
            try {
                var connection = DataSource.connection();

                PreparedStatement insertCategory = connection.prepareStatement("INSERT INTO dynamic_shop.category (name) VALUES (?) ON DUPLICATE KEY UPDATE id = id;");
                insertCategory.setString(1, category.getFileName());
                insertCategory.execute();
                insertCategory.close();
                Core.devLogger.log("Added " + category.getFileName() + " entry to the database");

                PreparedStatement checkForExisting = connection.prepareStatement("SELECT id FROM dynamic_shop.category WHERE name = ?;");
                checkForExisting.setString(1, category.getFileName());
                var i = checkForExisting.executeQuery();
                checkForExisting.close();
                if (i.next()) {
                    Core.gCore().getEngine()._LCACHE().getIdInfo().getCategoryIDs().put(category.getFileName(), i.getInt("id"));
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Core.devLogger.log(ObjectUtils.deserializeObjectToString(Core.gCore().getEngine()._LCACHE().getIdInfo().getCategoryIDs()));
    }

    //public static void createItems(ShopCategory category) {
    //    category.getOrderedItemList().forEach(shopItem -> {
    //        try {
    //            PreparedStatement insertItem = connection.prepareStatement(
    //                    "INSERT INTO dynamic_shop.item (material, buy_price, sell_price, category_id) VALUES (?, ?, ?, ?);"
    //            );
    //            insertItem.setString(1, shopItem.getMaterial().name());
    //            insertItem.setDouble(2, shopItem.getBuyPrice());
    //            insertItem.setDouble(3, shopItem.getSellPrice());
    //            insertItem.setInt(4, 0);
//
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //        }
    //    });
    //}

    // TODO: Add items to the db, link them with ID system in cache
}
