package com.sebbaindustries.dynamicshop.engine.ui.interfaces;

import com.sebbaindustries.dynamicshop.engine.ui.components.ClickActions;

public interface Clickable {

    ClickActions rightClick();

    ClickActions leftClick();

    ClickActions middleClick();

}
