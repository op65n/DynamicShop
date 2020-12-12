package com.sebbaindustries.dynamicshop.engine.components.gui.cache;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;

import java.util.HashMap;

public class UICache implements Cloneable {

    private String guiName = "Generic GUI name";
    private Integer size = -1;
    private UserInterfaceItem background;
    private HashMap<Integer, UserInterfaceItem> item = new HashMap<>();

    public UICache(UICache cache) {
        this.guiName = cache.getGuiName();
        this.size = cache.getSize();
        this.background = cache.getBackground();
        this.item = cache.getItem();
    }

    public String getGuiName() {
        return guiName;
    }

    public void setGuiName(String guiName) {
        this.guiName = guiName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public UserInterfaceItem getBackground() {
        return background;
    }

    public void setBackground(UserInterfaceItem background) {
        this.background = background;
    }

    public HashMap<Integer, UserInterfaceItem> getItem() {
        return item;
    }

    public void setItem(HashMap<Integer, UserInterfaceItem> item) {
        this.item = item;
    }
}
