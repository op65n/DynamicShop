package com.sebbaindustries.dynamicshop.engine.components;

import java.util.ArrayList;
import java.util.List;

public class ShopCategory {

    private String categoryName;
    private String formattedName;
    private List<ShopItem> items = new ArrayList<>();
    private int orderNo = -1;

    public ShopCategory(String name, String formattedName) {
        this.categoryName = name;
        this.formattedName = formattedName;
    }

    public String getName() {
        return categoryName;
    }

    public void setName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public void setItems(List<ShopItem> items) {
        this.items = items;
    }

    public void addItem(ShopItem item) {
        items.add(item);
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
