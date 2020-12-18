package com.sebbaindustries.dynamicshop.engine.components.gui.interfaces;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.ClickActions;

public interface Clickable {

    ClickActions rightClick();
    ClickActions leftClick();
    ClickActions middleClick();

}
