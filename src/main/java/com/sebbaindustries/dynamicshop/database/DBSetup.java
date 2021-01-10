package com.sebbaindustries.dynamicshop.database;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.structure.ItemStruct;
import com.sebbaindustries.dynamicshop.engine.structure.ShopCategoryStruct;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import com.sebbaindustries.dynamicshop.utils.ShopUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBSetup {

    private static final String CATEGORY_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`category` ( " +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `name` VARCHAR(64) NULL, " +
            "  `priority` INT NULL DEFAULT 1000, " +
            "  `icon_type` INT NULL DEFAULT 0, " +
            "  `icon_item` VARCHAR(512) NULL, " +
            "  `icon_display` VARCHAR(45) NULL, " +
            "  `icon_lore` VARCHAR(512) NULL, " +
            "  PRIMARY KEY (`id`), " +
            "  UNIQUE INDEX `name_UNIQUE` (`name` ASC)) " +
            "ENGINE = InnoDB;";

    private static final String ITEM_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`item` ( " +
            "  `id` INT NOT NULL AUTO_INCREMENT, " +
            "  `item_type` INT NULL DEFAULT 0, " +
            "  `item_material` VARCHAR(64) NOT NULL, " +
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
            "  `lore` VARCHAR(512) NULL DEFAULT NULL, " +
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

    public static void createCategories(List<ShopCategoryStruct> categories) {
        categories.forEach(category -> {
            try {
                var connection = DataSource.connection();

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static void insertNewCategory(Connection connection, ShopCategoryStruct category) throws SQLException {
        PreparedStatement insertCategory = connection.prepareStatement(
                "INSERT INTO dynamic_shop.category (name, priority, icon_type, icon_item, icon_display, icon_lore) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = id;"
        );

    }

    private static void insertItems(Connection connection, List<ItemStruct> shopItems, int catID) throws SQLException {
        for (var item : shopItems) {
            var insertItem = connection.prepareStatement(
                    "INSERT INTO dynamic_shop.item (item_type, item_material, price_buy, price_sell, category_id) VALUES (?, ?, ?, ?, ?);"
            );

            var pair = ShopUtils.getTypeAndItemPair(item.getMaterial(), item.getTexture(), item.getBase64());

            insertItem.setInt(1, pair.getLeft().type);
            insertItem.setString(2, pair.getRight());
            insertItem.setDouble(3, item.getPrice_buy());
            insertItem.setDouble(4, item.getPrice_sell());
            insertItem.setInt(5, catID);
            insertItem.execute();
            insertItem.close();

            Core.devLogger.log("Inserted " + item.getMaterial() + " into the database");

            PreparedStatement getGenItemID = connection.prepareStatement(
                    "SELECT id FROM dynamic_shop.item WHERE item_material = ? AND category_id = ? AND item_type = ?;"
            );
            getGenItemID.setString(1, pair.getRight());
            getGenItemID.setInt(2, catID);
            getGenItemID.setInt(3, pair.getLeft().type);

            var resID = getGenItemID.executeQuery();
            getGenItemID.close();

            if (resID.next()) {
                int itemID = resID.getInt("id");
                insertItemMeta(connection, item, catID, itemID);
            }
        }
    }

    private static void insertItemMeta(Connection connection, ItemStruct item, int catID, int itemID) throws SQLException {
        var insertMeta = connection.prepareStatement(
                "INSERT INTO dynamic_shop.item_meta (id, display, lore, priority, flatline, command, item_id, item_category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
        );

        insertMeta.setInt(1, itemID);
        insertMeta.setString(2, item.getDisplay());
        insertMeta.setString(3, ObjectUtils.deserializeObjectToString(item.getLore()));
        insertMeta.setInt(4, item.getPriority());
        insertMeta.setBoolean(5, item.getFlatline());
        insertMeta.setString(6, item.getCommand());
        insertMeta.setInt(7, itemID);
        insertMeta.setInt(8, catID);

        insertMeta.execute();
        insertMeta.close();

        Core.devLogger.log("Inserted " + item.getMaterial() + " meta data into the database");
    }




}
