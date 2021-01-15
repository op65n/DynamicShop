package tech.op65n.dynamicshop.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.cache.holders.ItemTableEntryHolder;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.components.SItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShopQuery {

    public ShopQuery(String database) {
        this.DATABASE = database;
    }

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    private String DATABASE = "dynamic_shop";

    private final String CAT_INDEX = "SELECT * FROM " + DATABASE + ".category;";
    private final String CAT_DEL_ITEM = "DELETE FROM " + DATABASE + ".item WHERE category_id = ?;";
    private final String CAT_DEL_CATEGORY = "DELETE FROM " + DATABASE + ".category WHERE id = ?;";
    private final String CAT_INS_NEW = "INSERT INTO " + DATABASE + ".category (filename) VALUE (?);";
    private final String CAT_GET_GEN_ID = "SELECT " + DATABASE + ".category.id FROM " + DATABASE + ".category WHERE filename = ? LIMIT 1;";

    public void indexCategories(Connection connection , List<SCategory> categoryList) throws SQLException {
        var fetch = connection.prepareStatement(CAT_INDEX);
        var resSet = fetch.executeQuery();
        fetch.close();

        while (resSet.next()) {
            int id = resSet.getInt("id");
            String filename = resSet.getString("filename");
            AtomicBoolean found = new AtomicBoolean(false);

            categoryList.forEach(category -> {
                if (!category.getFilename().equals(filename)) return;
                category.setID(id);
                Core.devLogger.log("Indexed: " + category.getFilename());
                found.set(true);
            });

            if (found.get()) continue;
            var deleteCatItm = connection.prepareStatement(CAT_DEL_ITEM);
            deleteCatItm.setInt(1, id);
            deleteCatItm.execute();
            deleteCatItm.close();

            var deleteCat = connection.prepareStatement(CAT_DEL_CATEGORY);
            deleteCat.setInt(1, id);
            deleteCat.execute();
            deleteCat.close();
            Core.devLogger.log("Removed: " + filename);
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

    private final String ITM_INDEX = "SELECT * FROM " + DATABASE + ".item;";
    private final String ITM_DEL = "DELETE FROM " + DATABASE + ".item WHERE id = ?;";
    private final String ITM_INS = "INSERT INTO " + DATABASE + ".item (item, price_buy, price_sell, item_history, config_price_buy, config_price_sell, category_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final String ITM_GET_GEN_ID = "SELECT " + DATABASE + ".item.id FROM " + DATABASE + ".item WHERE item = ? AND category_id = ?;";

    public void indexItems(Connection connection , List<SCategory> categoryList) throws SQLException {
        var fetch = connection.prepareStatement(ITM_INDEX);
        var resSet = fetch.executeQuery();
        fetch.close();

        while (resSet.next()) {
            ItemTableEntryHolder holder = new ItemTableEntryHolder(resSet);
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
                insertNewItem.setDouble(2, item.getItemPricing().getCnfPriceBuy());
                insertNewItem.setDouble(3, item.getItemPricing().getCnfPriceSell());
                insertNewItem.setString(4, gson.toJson(item.getHistory()));
                insertNewItem.setDouble(5, item.getItemPricing().getCnfPriceBuy());
                insertNewItem.setDouble(6, item.getItemPricing().getCnfPriceSell());
                insertNewItem.setInt(7, category.getID());
                insertNewItem.execute();
                insertNewItem.close();

                var getGeneratedItemID = connection.prepareStatement(ITM_GET_GEN_ID);
                getGeneratedItemID.setString(1, item.getItem());
                getGeneratedItemID.setInt(2, category.getID());
                var genItemID = getGeneratedItemID.executeQuery();
                getGeneratedItemID.close();

                if (!genItemID.next()) {
                    Core.engineLogger.logSevere("Error got item with no/Illegal index!");
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

    private final String UPDATE_DS = "UPDATE " + DATABASE + ".item SET price_buy = ?, price_sell = ?, item_buys = ?, item_sells = ?, item_history = ? WHERE item.id = ?;";

    public void updateDataSource(Connection connection, SCategory category) throws SQLException {
        for (SItem item : category.getItems()) {
            if (!item.isNeedsDBUpdate()) continue;
            var update = connection.prepareStatement(UPDATE_DS);
            update.setDouble(1, item.getItemPricing().getPriceBuy());
            update.setDouble(2, item.getItemPricing().getPriceSell());
            update.setInt(3, item.getItemPricing().getBuys());
            update.setInt(4, item.getItemPricing().getSells());
            update.setString(5, gson.toJson(item.getHistory()));
            update.setInt(6, item.getID());
            update.execute();
            update.close();
        }
    }

}
