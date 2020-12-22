package com.sebbaindustries.dynamicshop.engine.components.gui.interfaces;

public interface UserInterface {

    void open();

    void update();

    void updateUISlots();

    void close();

    void onRightClick(int slot);

    void onLeftClick(int slot);

    void onMiddleClick(int slot);

}
