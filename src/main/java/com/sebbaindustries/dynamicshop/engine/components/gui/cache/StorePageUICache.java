package com.sebbaindustries.dynamicshop.engine.components.gui.cache;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.ItemsPanel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StorePageUICache {

    private String name = "$NULL";
    private int size = -1;

    private UIBackground background;
    private ItemsPanel items;
    private List<UIButton> button = new ArrayList<>();

}
