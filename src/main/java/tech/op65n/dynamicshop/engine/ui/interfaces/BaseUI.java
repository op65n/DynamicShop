package tech.op65n.dynamicshop.engine.ui.interfaces;

import tech.op65n.dynamicshop.engine.ui.components.UIBackground;
import tech.op65n.dynamicshop.engine.ui.components.UIButton;

import java.util.List;

public interface BaseUI {

    String name();

    int size();

    void setSize(int size);

    UIBackground background();

    List<UIButton> buttons();

}
