package com.sebbaindustries.dynamicshop.engine.components.gui.components;

public interface UserInterface {

    void open();

    void update();

    void close();

    void onRightClick(int slot);

    void onLeftClick(int slot);

    void onMiddleClick(int slot);

    void setMetaData(UIMetaData metaData);

    UIMetaData getMetaData();


}
