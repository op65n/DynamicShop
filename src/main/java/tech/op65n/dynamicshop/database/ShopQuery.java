package tech.op65n.dynamicshop.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.components.EItemType;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.components.SItem;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import tech.op65n.dynamicshop.engine.structure.ShopCategoryStruct;
import tech.op65n.dynamicshop.utils.ShopUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ShopQuery {

    public ShopQuery(String database) {
        this.DATABASE = database;
    }

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    private String DATABASE = "dynamic_shop";

    /*
    -- -----------------------------------------------------
    -- Table `dynamic_shop`.`category`
    -- -----------------------------------------------------
     */
    private final String INSERT_CATEGORY = (
            "INSERT INTO " + DATABASE + ".category (name, priority, icon_type, icon_item, icon_display, icon_lore) " +
                    "VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = id;"
    );

    /**
     * Insert category query method for the database
     *
     * @param connection Hikari pool connection
     * @param categoryStruct Shop category structure
     * @throws SQLException Exception from database
     */
    public void createCategory(Connection connection, ShopCategoryStruct categoryStruct) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_CATEGORY);

        var pair = ShopUtils.getTypeAndItemPair(
                categoryStruct.getIcon().getMaterial(),
                categoryStruct.getIcon().getTexture(),
                categoryStruct.getIcon().getBase64()
        );

        statement.setString(1, categoryStruct.getFilename());
        statement.setInt(2, categoryStruct.getPriority());
        statement.setInt(3, pair.getLeft().type);
        statement.setString(4, pair.getRight());
        statement.setString(5, categoryStruct.getIcon().getDisplay());
        statement.setString(6, gson.toJson(categoryStruct.getIcon().getLore()));

        statement.execute();
        statement.close();

        Core.devLogger.log("Added " + categoryStruct.getFilename() + " category to the database");
    }

    /*
    -- -----------------------------------------------------
    -- Table `dynamic_shop`.`category`
    -- -----------------------------------------------------
     */
    private final String SELECT_CATEGORIES = "SELECT * FROM " + DATABASE + ".category;";

    /**
     * Selects categories and loads them to a local cache.
     *
     * @param connection Hikari pool connection
     * @param indexedCategoryCache Category cache
     * @throws SQLException Exception from database
     */
    public void loadCategories(Connection connection, ConcurrentHashMap<Integer, SCategory> indexedCategoryCache) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORIES);
        var fetch = statement.executeQuery();

        while (fetch.next()) {
            SCategory category = new SCategory();
            int catID = fetch.getInt("id");
            category.setID(catID);
            category.setPriority(fetch.getInt("priority"));
            category.getIcon().setItem(fetch.getString("icon_item"));
            switch (fetch.getInt("icon_type")) {
                case 0 -> category.getIcon().setType(EItemType.MATERIAL);
                case 1 -> category.getIcon().setType(EItemType.TEXTURE);
                case 2 -> category.getIcon().setType(EItemType.BASE64);
            }
            category.getIcon().setDisplay(fetch.getString("icon_display"));
            category.getIcon().setLore(gson.fromJson(fetch.getString("icon_lore"), new TypeToken<List<String>>() {}.getType()));

            indexedCategoryCache.put(catID, category);
        }
    }

    /*
    -- -----------------------------------------------------
    -- Table `dynamic_shop`.`item`
    -- -----------------------------------------------------
     */
    private final String INSERT_ITEM = "INSERT INTO " + DATABASE + ".item (item_type, item_material, price_buy, price_sell, category_id) VALUES (?, ?, ?, ?, ?);";
    private final String GET_INSERTED_ID = "SELECT id FROM " + DATABASE + ".item WHERE item_material = ? AND item_type = ? AND category_id = ?;";
    /*
    -- -----------------------------------------------------
    -- Table `dynamic_shop`.`item_meta`
    -- -----------------------------------------------------
     */
    private final String INSERT_ITEM_META = "INSERT INTO " + DATABASE + ".item_meta (id, display, lore, priority, flatline, command, item_id, item_category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    /**
     * Inserts item and item_meta entries to the databse
     *
     * @param connection Hikari pool connection
     * @param catID category ID
     * @param item Item Structure
     * @throws SQLException Exception from database
     */
    public void createItem(Connection connection, int catID, ItemStruct item) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_ITEM);

        var pair = ShopUtils.getTypeAndItemPair(item.getMaterial(), item.getTexture(), item.getBase64());

        statement.setInt(1, pair.getLeft().type);
        statement.setString(2, pair.getRight());
        statement.setDouble(3, item.getPrice_buy());
        statement.setDouble(4, item.getPrice_sell());
        statement.setInt(5, catID);
        statement.execute();
        statement.close();

        PreparedStatement getGeneratedID = connection.prepareStatement(GET_INSERTED_ID);
        getGeneratedID.setString(1, pair.getRight());
        getGeneratedID.setInt(2, pair.getLeft().type);
        getGeneratedID.setInt(3, catID);
        var resID = getGeneratedID.executeQuery();
        getGeneratedID.close();

        if (!resID.next()) return;
        int itemID = resID.getInt("id");

        PreparedStatement addItemMeta = connection.prepareStatement(INSERT_ITEM_META);

        addItemMeta.setInt(1, itemID);
        addItemMeta.setString(2, item.getDisplay());
        addItemMeta.setString(3, gson.toJson(item.getLore()));
        addItemMeta.setInt(4, item.getPriority());
        addItemMeta.setBoolean(5, item.getFlatline());
        addItemMeta.setString(6, item.getCommand());
        addItemMeta.setInt(7, itemID);
        addItemMeta.setInt(8, catID);
        addItemMeta.execute();
        addItemMeta.close();
    }

    /*
    -- -----------------------------------------------------
    -- Table `dynamic_shop`.`item`
    -- -----------------------------------------------------
     */
    private final String SELECT_ITEMS = "SELECT * FROM " + DATABASE + ".item;";

    /**
     *
     * @param connection Hikari pool connection
     * @param category Shop category class
     * @throws SQLException Exception from database
     */
    public void loadItems(Connection connection, SCategory category) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_ITEMS);
        var fetch = statement.executeQuery();

        while (fetch.next()) {
            SItem item = new SItem();
            item.setID(fetch.getInt("id"));
            item.setItem(fetch.getString("item_material"));
            switch (fetch.getInt("item_type")) {
                case 0 -> item.setType(EItemType.MATERIAL);
                case 1 -> item.setType(EItemType.TEXTURE);
                case 2 -> item.setType(EItemType.BASE64);
            }
            item.getItemPricing().setPriceBuy(fetch.getDouble("price_sell"));
            item.getItemPricing().setPriceSell(fetch.getDouble("price_buy"));
            item.getItemPricing().setBuys(fetch.getInt("buys"));
            item.getItemPricing().setSells(fetch.getInt("sells"));
            item.setCatID(fetch.getInt("category_id"));
            category.getItems().put(item.getID(), item);
        }
    }

}
