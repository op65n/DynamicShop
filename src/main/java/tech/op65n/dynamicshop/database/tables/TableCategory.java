package tech.op65n.dynamicshop.database.tables;

import lombok.Getter;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableCategory implements DatabaseTable {

    @Override
    public int priority() {
        return 0;
    }

    @Override
    @Language("MariaDB")
    public String createIfNotExists() {
        return "CREATE TABLE IF NOT EXISTS `dynamic_shop`.`category` ( " +
                "  `id` INT NOT NULL AUTO_INCREMENT, " +
                "  `filename` VARCHAR(128) NOT NULL, " +
                "  PRIMARY KEY (`id`), " +
                "  UNIQUE INDEX `filename_UNIQUE` (`filename` ASC)) " +
                "ENGINE = InnoDB;";
    }

    @Getter
    public static class Holder {

        private final int id;
        private final String filename;

        public Holder(ResultSet set) throws SQLException {
            this.id = set.getInt("id");
            this.filename = set.getString("filename");
        }

    }

}
