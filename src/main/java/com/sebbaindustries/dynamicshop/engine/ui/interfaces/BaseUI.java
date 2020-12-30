package com.sebbaindustries.dynamicshop.engine.ui.interfaces;

import com.sebbaindustries.dynamicshop.engine.ui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.ui.components.UIButton;

import java.util.List;

public interface BaseUI {

    String name();

    int size();

    void setSize(int size);

    UIBackground background();

    List<UIButton> buttons();

}
