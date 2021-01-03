package com.sebbaindustries.dynamicshop.database;

import com.sebbaindustries.dynamicshop.engine.task.Task;

import java.sql.SQLException;

public class DBSetup {

    private final String ITEM_META_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`item_meta` (" +
            "  `id` INT NOT NULL," +
            "  `display` VARCHAR(64) NULL DEFAULT '%item%'," +
            "  `lore` VARCHAR(512) NULL DEFAULT NULL," +
            "  `priority` INT NULL DEFAULT 1000," +
            "  `flatline` TINYINT NULL DEFAULT 0," +
            "  `base64` VARCHAR(512) NULL DEFAULT NULL," +
            "  `command` VARCHAR(256) NULL DEFAULT NULL," +
            "  PRIMARY KEY (`id`))" +
            "ENGINE = InnoDB;"
            ;

    private final String ITEM_TABLE =
            "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`item` (" +
            "  `id` INT NOT NULL," +
            "  `material` VARCHAR(64) NOT NULL," +
            "  `buy_price` DOUBLE NULL DEFAULT NULL," +
            "  `sell_price` DOUBLE NULL DEFAULT NULL," +
            "  `buys` INT NOT NULL DEFAULT 0," +
            "  `sells` INT NOT NULL DEFAULT 0," +
            "  `item_meta_id` INT NOT NULL," +
            "  PRIMARY KEY (`id`))" +
            "ENGINE = InnoDB;"
            ;

    public void create() {
        Task.async(() -> {
            try {
                DataSource.connection().prepareStatement(ITEM_TABLE).executeUpdate();
                DataSource.connection().prepareStatement(ITEM_META_TABLE).executeUpdate();
                DataSource.connection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
