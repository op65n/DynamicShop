package tech.op65n.dynamicshop.engine.ui.cache;

import tech.op65n.dynamicshop.engine.ui.components.ItemsPanel;
import tech.op65n.dynamicshop.engine.ui.components.UIBackground;
import tech.op65n.dynamicshop.engine.ui.components.UIButton;
import tech.op65n.dynamicshop.engine.ui.interfaces.BaseUI;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class StorePageUICache implements BaseUI {

    private String name = "$NULL";
    private List<UIButton> button = new ArrayList<>();
    private int size = 1;
    private UIBackground background;
    @Getter
    @Setter
    private ItemsPanel items;

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int size() {
        if (size > 6) return 6;
        return Math.max(size, 1);
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
