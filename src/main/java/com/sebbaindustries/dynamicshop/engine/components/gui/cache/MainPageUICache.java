package com.sebbaindustries.dynamicshop.engine.components.gui.cache;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.Background;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.Button;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MainPageUICache {

    private String name = "$NULL";
    private int size = -1;

    private Background background;
    private List<Button> button = new ArrayList<>();
    private List<Category> category = new ArrayList<>();

}
