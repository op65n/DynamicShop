package tech.op65n.dynamicshop.database;

import tech.op65n.dynamicshop.database.tables.DatabaseTable;
import tech.op65n.dynamicshop.database.tables.TableCategory;
import tech.op65n.dynamicshop.database.tables.TableItem;
import tech.op65n.dynamicshop.database.tables.TableTransaction;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataSourceSetup {

    public static boolean createTables() {
        List<DatabaseTable> tables = DatabaseTable.registerTables(
                new TableCategory(),
                new TableItem(),
                new TableTransaction()
        );

        try {
            var connection = DataSource.connection();

            for (DatabaseTable table : tables) {
                PreparedStatement statement = connection.prepareStatement(table.createIfNotExists());
                statement.execute();
                statement.close();
            }

            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
