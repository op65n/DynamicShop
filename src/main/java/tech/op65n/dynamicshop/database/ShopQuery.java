package tech.op65n.dynamicshop.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.database.tables.TableCategory;
import tech.op65n.dynamicshop.database.tables.TableItem;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.components.SItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShopQuery {

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private String DATABASE = "dynamic_shop";

    @Language("MariaDB")
    private final String CAT_INDEX = "SELECT * FROM " + DATABASE + ".category;";
    @Language("MariaDB")
    private final String CAT_DEL_ITEM = "DELETE FROM " + DATABASE + ".item WHERE category_id = ?;";
    @Language("MariaDB")
    private final String CAT_DEL_CATEGORY = "DELETE FROM " + DATABASE + ".category WHERE id = ?;";
    @Language("MariaDB")
    private final String CAT_INS_NEW = "INSERT INTO " + DATABASE + ".category (filename) VALUE (?);";
    @Language("MariaDB")
    private final String CAT_GET_GEN_ID = "SELECT " + DATABASE + ".category.id FROM " + DATABASE + ".category WHERE filename = ? LIMIT 1;";
    @Language("MariaDB")
    private final String ITM_INDEX = "SELECT * FROM " + DATABASE + ".item;";
    @Language("MariaDB")
    private final String ITM_DEL = "DELETE FROM " + DATABASE + ".item WHERE id = ?;";
    @Language("MariaDB")
    private final String ITM_INS = "INSERT INTO " + DATABASE + ".item (item, price_buy, price_sell, config_price_buy, config_price_sell, category_id) VALUES (?, ?, ?, ?, ?, ?);";
    @Language("MariaDB")
    private final String ITM_GET_GEN_ID = "SELECT " + DATABASE + ".item.id FROM " + DATABASE + ".item WHERE item = ? AND category_id = ?;";
    @Language("MariaDB")
    private final String UPDATE_DS = "UPDATE " + DATABASE + ".item SET price_buy = ?, price_sell = ?, item_buys = ?, item_sells = ? WHERE item.id = ?;";

    public ShopQuery(String database) {
        this.DATABASE = database;
    }

    public void indexCategories(@NotNull Connection connection, List<SCategory> categoryList) throws SQLException {
        var fetch = connection.prepareStatement(CAT_INDEX);
        var resSet = fetch.executeQuery();
        fetch.close();

        while (resSet.next()) {
            TableCategory.Holder holder = new TableCategory.Holder(resSet);
            AtomicBoolean found = new AtomicBoolean(false);

            categoryList.forEach(category -> {
                if (!category.getFilename().equals(holder.getFilename())) return;
                category.setID(holder.getId());
                Core.devLogger.log("Indexed: " + category.getFilename());
                found.set(true);
            });

            if (found.get()) continue;
            var deleteCatItm = connection.prepareStatement(CAT_DEL_ITEM);
            deleteCatItm.setInt(1, holder.getId());
            deleteCatItm.execute();
            deleteCatItm.close();

            var deleteCat = connection.prepareStatement(CAT_DEL_CATEGORY);
            deleteCat.setInt(1, holder.getId());
            deleteCat.execute();
            deleteCat.close();
            Core.devLogger.log("Removed: " + holder.getFilename());
        }

        for (SCategory category : categoryList) {
            if (category.getID() != -1) continue;
            var insertCat = connection.prepareStatement(CAT_INS_NEW);
            insertCat.setString(1, category.getFilename());
            insertCat.execute();
            insertCat.close();

            int listIndex = categoryList.indexOf(category);

            var getGeneratedCatID = connection.prepareStatement(CAT_GET_GEN_ID);
            getGeneratedCatID.setString(1, category.getFilename());
            var idResSet = getGeneratedCatID.executeQuery();
            getGeneratedCatID.close();

            if (!idResSet.next()) {
                Core.engineLogger.logSevere("Error got category with no/Illegal index!");
                continue;
            }
            categoryList.get(listIndex).setID(idResSet.getInt("id"));
            Core.devLogger.log("Added: " + category.getFilename());
        }
    }

    public void indexItems(@NotNull Connection connection, List<SCategory> categoryList) throws SQLException {
        var fetch = connection.prepareStatement(ITM_INDEX);
        var resSet = fetch.executeQuery();
        fetch.close();

        while (resSet.next()) {
            TableItem.Holder holder = new TableItem.Holder(resSet);
            AtomicBoolean found = new AtomicBoolean(false);

            categoryList.forEach(category -> {
                if (category.getID() != holder.getCategoryID()) return;
                category.getItems().forEach(item -> {
                    if (!item.getItem().equals(holder.getItem())) return;
                    item.applyDBWrapper(holder);
                    found.set(true);
                    Core.devLogger.log("Indexed: " + item.getItem());
                });
            });

            if (found.get()) continue;
            var deleteItm = connection.prepareStatement(ITM_DEL);
            deleteItm.setInt(1, holder.getId());
            deleteItm.execute();
            deleteItm.close();
            Core.devLogger.log("Removed: " + holder.getItem());
        }

        for (SCategory category : categoryList) {
            if (category.getID() == -1) {
                Core.engineLogger.logSevere("Error got category with Illegal index (-1)!");
                continue;
            }
            int categoryIndex = categoryList.indexOf(category);

            for (SItem item : category.getItems()) {
                if (item.getID() != -1) continue;
                int itemIndex = category.getItems().indexOf(item);

                var insertNewItem = connection.prepareStatement(ITM_INS);
                insertNewItem.setString(1, item.getItem());
                insertNewItem.setObject(2, item.getItemPricing().getCnfPriceBuy(), Types.DOUBLE);
                insertNewItem.setObject(3, item.getItemPricing().getCnfPriceSell(), Types.DOUBLE);
                insertNewItem.setObject(4, item.getItemPricing().getCnfPriceBuy(), Types.DOUBLE);
                insertNewItem.setObject(5, item.getItemPricing().getCnfPriceSell(), Types.DOUBLE);
                insertNewItem.setInt(6, category.getID());
                insertNewItem.execute();
                insertNewItem.close();

                var getGeneratedItemID = connection.prepareStatement(ITM_GET_GEN_ID);
                getGeneratedItemID.setString(1, item.getItem());
                getGeneratedItemID.setInt(2, category.getID());
                var genItemID = getGeneratedItemID.executeQuery();
                getGeneratedItemID.close();

                if (!genItemID.next()) {
                    Core.engineLogger.logSevere("Error got item with no or illegal index!");
                    continue;
                }

                item.setID(genItemID.getInt("id"));
                item.setCatID(category.getID());
                item.getItemPricing().setPriceBuy(item.getItemPricing().getCnfPriceBuy());
                item.getItemPricing().setPriceSell(item.getItemPricing().getCnfPriceSell());

                categoryList.get(categoryIndex).getItems().set(itemIndex, item);
                Core.devLogger.log("Added: " + item.getItem());
            }
        }
    }

    public void updateDataSource(@NotNull Connection connection, SCategory category) throws SQLException {
        for (SItem item : category.getItems()) {
            if (!item.isNeedsDBUpdate()) continue;
            var update = connection.prepareStatement(UPDATE_DS);
            update.setObject(1, item.getItemPricing().getPriceBuy(), Types.DOUBLE);
            update.setObject(2, item.getItemPricing().getPriceSell(), Types.DOUBLE);
            update.setInt(3, item.getItemPricing().getBuys());
            update.setInt(4, item.getItemPricing().getSells());
            update.setInt(5, item.getID());
            update.execute();
            update.close();

            Core.devLogger.log("Updated: " + item.getItem());
        }
    }

}
