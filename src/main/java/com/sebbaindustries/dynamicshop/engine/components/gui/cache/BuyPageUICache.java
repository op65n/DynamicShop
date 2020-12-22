package com.sebbaindustries.dynamicshop.engine.components.gui.cache;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BuyPageUICache {

    private String name = "$NULL";
    private int size = 6;
    private int itemSlot = 0;

    private UIBackground background;
    private ShopItem item;
    private List<UIButton> button = new ArrayList<>();

}
