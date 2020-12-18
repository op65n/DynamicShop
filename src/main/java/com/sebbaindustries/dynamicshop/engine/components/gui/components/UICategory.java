package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.Clickable;
import com.sebbaindustries.dynamicshop.engine.components.shop.ShopCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UICategory implements Clickable {

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

}
