package com.sebbaindustries.dynamicshop.engine.ui.components;

import com.sebbaindustries.dynamicshop.engine.components.SCategory;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import com.sebbaindustries.dynamicshop.engine.ui.interfaces.Clickable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.Base64;
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
        // TODO: Fix this
        return 0;
    }

    @Override
    public Material material() {
        // TODO: Fix this
        return null;
    }

    @Override
    public String display() {
        // TODO: Fix this
        return null;
    }

    @Override
    public List<String> lore() {
        // TODO: Fix this
        return null;
    }

    @Override
    public String texture() {
        // TODO: Fix this
        return null;
    }

    @Override
    public byte[] base64() {
        //if (category.base64() == null) return null;
        //return Base64.getDecoder().decode(category.base64());
        // TODO: Fix this
        return null;
    }
}
