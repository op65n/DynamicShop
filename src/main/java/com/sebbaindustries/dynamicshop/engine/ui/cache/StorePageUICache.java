package com.sebbaindustries.dynamicshop.engine.ui.cache;

import com.sebbaindustries.dynamicshop.engine.ui.components.ItemsPanel;
import com.sebbaindustries.dynamicshop.engine.ui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.ui.components.UIButton;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StorePageUICache {

    private String name = "$NULL";
    private int size = 6;

    private UIBackground background;
    private ItemsPanel items;
    private List<UIButton> button = new ArrayList<>();

}
