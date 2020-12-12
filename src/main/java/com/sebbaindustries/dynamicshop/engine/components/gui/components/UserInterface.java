package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import org.bukkit.entity.Player;

public interface UserInterface {

    void open(Player player);

    void update();

    void close();

    void onRightClick(int slot);

    void onLeftClick(int slot);

    void onMiddleClick(int slot);

    void setMetaData(UIMetaData metaData);

    UIMetaData getMetaData();


}
