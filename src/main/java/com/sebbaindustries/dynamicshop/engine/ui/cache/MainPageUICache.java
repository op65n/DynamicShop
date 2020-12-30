package com.sebbaindustries.dynamicshop.engine.ui.cache;

import com.sebbaindustries.dynamicshop.engine.ui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.ui.components.UIButton;
import com.sebbaindustries.dynamicshop.engine.ui.components.UICategory;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.BaseUI;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class MainPageUICache implements BaseUI {

    private final String name = "$NULL";

    private int size = 6;

    private UIBackground background;

    private final List<UIButton> button = new ArrayList<>();

    @Getter
    @Setter
    private List<UICategory> category = new ArrayList<>();

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public UIBackground background() {
        return this.background;
    }

    @Override
    public List<UIButton> buttons() {
        return this.button;
    }
}
