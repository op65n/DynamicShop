package com.sebbaindustries.dynamicshop.engine.components.gui.components;

public class UIAction {

    public enum Actions {
        OPEN,
        CLOSE,
        BACK,

        BUY,
        SELL,

        CANCEL,
        ;
    }

    private Actions action;

    public boolean hasAction() {
        return action != null;
    }

    public Actions get() {
        return action;
    }
}
