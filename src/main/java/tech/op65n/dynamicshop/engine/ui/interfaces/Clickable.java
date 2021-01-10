package tech.op65n.dynamicshop.engine.ui.interfaces;

import tech.op65n.dynamicshop.engine.ui.components.ClickActions;

public interface Clickable {

    ClickActions rightClick();

    ClickActions leftClick();

    ClickActions middleClick();

}
