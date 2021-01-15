package tech.op65n.dynamicshop.engine.cache.holders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import tech.op65n.dynamicshop.engine.components.SItemHistory;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class ItemTableEntryHolder {

    private final int id;
    private final String item;
    private final double priceBuy;
    private final double priceSell;
    private final int buys;
    private final int sells;
    private final SItemHistory history;
    private final double cnfPriceBuy;
    private final double cnfPriceSell;
    private final int categoryID;

    public ItemTableEntryHolder(ResultSet set) throws SQLException {
        final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        this.id = set.getInt("id");
        this.item = set.getString("item");
        this.priceBuy = set.getDouble("price_buy");
        this.priceSell = set.getDouble("price_sell");
        this.buys = set.getInt("item_buys");
        this.sells = set.getInt("item_sells");
        this.history = gson.fromJson(set.getString("item_history"), SItemHistory.class);
        this.cnfPriceBuy = set.getDouble("config_price_buy");
        this.cnfPriceSell = set.getDouble("config_price_sell");
        this.categoryID = set.getInt("category_id");
    }

}
