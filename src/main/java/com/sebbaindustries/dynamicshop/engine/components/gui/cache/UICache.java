package com.sebbaindustries.dynamicshop.engine.components.gui.cache;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UserInterfaceItem;

import java.util.HashMap;

public class UICache  {

    private final String guiName = "Generic GUI name";
    private final Integer size = -1;
    private UserInterfaceItem background;
    private final HashMap<Integer, UserInterfaceItem> item = new HashMap<>();


    public String getGuiName() {
        return guiName;
    }

    public Integer getSize() {
        return size;
    }

    public UserInterfaceItem getBackground() {
        return background;
    }

    public HashMap<Integer, UserInterfaceItem> getItem() {
        return item;
    }
}
