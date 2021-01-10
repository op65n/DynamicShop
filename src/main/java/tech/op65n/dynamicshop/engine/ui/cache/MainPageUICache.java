package tech.op65n.dynamicshop.engine.ui.cache;

import tech.op65n.dynamicshop.engine.ui.components.UIBackground;
import tech.op65n.dynamicshop.engine.ui.components.UIButton;
import tech.op65n.dynamicshop.engine.ui.components.UICategory;
import tech.op65n.dynamicshop.engine.ui.interfaces.BaseUI;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class MainPageUICache implements BaseUI {

    private final String name = "$NULL";
    private final List<UIButton> button = new ArrayList<>();
    private int size = 6;
    private UIBackground background;
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
