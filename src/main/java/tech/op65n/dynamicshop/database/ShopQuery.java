package tech.op65n.dynamicshop.database;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.components.EItemType;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.structure.ShopCategoryStruct;
import tech.op65n.dynamicshop.utils.ObjectUtils;
import tech.op65n.dynamicshop.utils.ShopUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class ShopQuery {

    public ShopQuery(String database) {
        this.DATABASE = database;
    }

    private String DATABASE = "dynamic_shop";

    /*
    Insert category query for the database
     */
    private final String INSERT_CATEGORY = (
            "INSERT INTO " + DATABASE + ".category (name, priority, icon_type, icon_item, icon_display, icon_lore) " +
                    "VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = id;"
    );

    /**
     * Insert category query method for the database
     *
     * @param connection Hikari pool connection.
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
        statement.setString(6, ObjectUtils.deserializeObjectToString(categoryStruct.getIcon().getLore()));

        statement.execute();
        statement.close();

        Core.devLogger.log("Added " + categoryStruct.getFilename() + " category to the database");
    }

    /*
    Select category query for the database
     */
    private final String SELECT_CATEGORIES = "SELECT * FROM " + DATABASE + ".category;";

    /**
     * Selects categories and loads them to a local cache.
     *
     * @param connection Hikari pool connection.
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
            //category.getIcon().setLore(fetch.getString("icon_lore"));


            indexedCategoryCache.put(catID, category);
        }
    }

}
