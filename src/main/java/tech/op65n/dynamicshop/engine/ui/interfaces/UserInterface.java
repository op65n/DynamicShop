package tech.op65n.dynamicshop.engine.ui.interfaces;

import org.bukkit.event.inventory.ClickType;

public interface UserInterface {

    void open();

    void update();

    void updateUISlots(boolean updateCurrent);

    void close();

    void onClick(int slot, ClickType clickType);
}
