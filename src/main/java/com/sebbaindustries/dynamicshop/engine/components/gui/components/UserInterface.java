package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import org.bukkit.entity.Player;

public interface UserInterface {

    void open(Player player);

    void draw();
    void clear();
    void update();
    void close();

    void setMetaData(UIMetaData metaData);
    UIMetaData getMetaData();


}
