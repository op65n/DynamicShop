package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemsPanel {

    private int cornerA = 1;
    private int cornerB = 7;
    private boolean collapsed = false;

    private ClickActions onClick = ClickActions.NA;
    private ClickActions onRightClick = ClickActions.NA;
    private ClickActions onLeftClick = ClickActions.NA;
    private ClickActions onMiddleClick = ClickActions.NA;
}
