package tech.op65n.dynamicshop.database.tables;

import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface DatabaseTable {

    int priority();

    String createIfNotExists();

    static List<DatabaseTable> registerTables(DatabaseTable... table) {
        List<DatabaseTable> tables = new ArrayList<>();
        Arrays.stream(table).forEach(regTable -> {
            if (regTable.createIfNotExists() == null) throw new NotImplementedException("Received a null table creation statement, aborting!");
            tables.add(regTable);
        });
        tables.sort(Comparator.comparing(DatabaseTable::priority));
        return tables;
    }

}
