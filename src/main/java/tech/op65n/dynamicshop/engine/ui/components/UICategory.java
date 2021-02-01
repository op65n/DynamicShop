package tech.op65n.dynamicshop.engine.ui.components;

import org.apache.commons.lang3.tuple.Pair;
import tech.op65n.dynamicshop.engine.components.EItemType;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import tech.op65n.dynamicshop.engine.ui.interfaces.Clickable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UICategory implements BukkitItemStack, Clickable {

    private int slot;
    private SCategory category = null;

    private ClickActions onClick = ClickActions.NA;
    private ClickActions onRightClick = ClickActions.NA;
    private ClickActions onLeftClick = ClickActions.NA;
    private ClickActions onMiddleClick = ClickActions.NA;

    @Override
    public ClickActions rightClick() {
        if (this.onRightClick == ClickActions.NA) return this.onClick;
        return this.onRightClick;
    }

    @Override
    public ClickActions leftClick() {
        if (this.onLeftClick == ClickActions.NA) return this.onClick;
        return this.onLeftClick;
    }

    @Override
    public ClickActions middleClick() {
        if (this.onMiddleClick == ClickActions.NA) return this.onClick;
        return this.onMiddleClick;
    }

    @Override
    public int amount() {
        return 1;
    }

    @Override
    public Pair<EItemType, String> material() {
        return Pair.of(category.getIcon().getType(), category.getIcon().getItem());
    }

    @Override
    public String display() {

        return category.getIcon().getDisplay();
    }

    @Override
    public List<String> lore() {
        return category.getIcon().getLore();
    }
}
