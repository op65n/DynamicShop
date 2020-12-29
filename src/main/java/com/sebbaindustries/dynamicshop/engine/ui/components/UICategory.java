package com.sebbaindustries.dynamicshop.engine.ui.components;

import com.sebbaindustries.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UICategory implements BukkitItemStack, Clickable {

    private int slot;
    private ShopCategory category = null;

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
        return category.amount();
    }

    @Override
    public Material material() {
        return category.material();
    }

    @Override
    public String display() {
        return category.display();
    }

    @Override
    public List<String> lore() {
        return category.lore();
    }
}
