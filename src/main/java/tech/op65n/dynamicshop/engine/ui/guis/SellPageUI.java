package tech.op65n.dynamicshop.engine.ui.guis;

import org.bukkit.inventory.ItemStack;
import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.components.SItem;
import tech.op65n.dynamicshop.engine.ui.cache.InventoryHolderCache;
import tech.op65n.dynamicshop.engine.ui.cache.SellPageUICache;
import tech.op65n.dynamicshop.engine.ui.components.ClickActions;
import tech.op65n.dynamicshop.engine.ui.components.UIButton;
import tech.op65n.dynamicshop.engine.ui.interfaces.BaseUI;
import tech.op65n.dynamicshop.engine.ui.interfaces.Clickable;
import tech.op65n.dynamicshop.engine.ui.interfaces.UserInterface;
import tech.op65n.dynamicshop.utils.Color;
import tech.op65n.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.TreeMap;

public class SellPageUI implements UserInterface {

    private final Player player;
    private final BaseUI cache;
    private final SCategory backPage;
    private final Map<Integer, Object> mappedInventory = new TreeMap<>();
    private final SItem selectedItem;
    private Inventory inventory;

    public SellPageUI(Player player, SItem item, SCategory backPage) {
        this.player = player;
        this.cache = Core.gCore().getEngine().ui().getSellPageCache();
        this.selectedItem = item;
        this.backPage = backPage;

        inventory = Bukkit.createInventory(null, cache.size() * 9, Color.format(cache.name()));

        // Update/flush cache
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void open() {
        player.openInventory(inventory);
        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void update() {
        updateUISlots(false);

        cache.setSize(UserInterfaceUtils.calculateInventorySize(mappedInventory));

        String guiName = cache.name().replace("%item%", selectedItem.getMetadata().getDisplay());

        inventory = Bukkit.createInventory(null, cache.size() * 9, Color.format(guiName));
        UserInterfaceUtils.setupInventory(inventory, mappedInventory, cache.size());

        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void updateUISlots(boolean updateCurrent) {
        mappedInventory.clear();

        /*
        background
         */
        UserInterfaceUtils.createBackground(mappedInventory, cache.background(), cache.size());

        /*
        buttons
         */
        UserInterfaceUtils.mapButtons(mappedInventory, cache, selectedItem);

        /*
        item
         */
        UserInterfaceUtils.mapItem(mappedInventory, selectedItem, ((SellPageUICache) cache).getItem());

        InventoryHolderCache.cache(player, this);

        if (!updateCurrent) return;
        UserInterfaceUtils.clearUI(player);
        UserInterfaceUtils.fillInventory(player, mappedInventory, cache.size());
        player.updateInventory();
    }

    @Override
    public void close() {
        player.closeInventory();
        InventoryHolderCache.removeIfPresent(player);
    }

    @Override
    public void onClick(int slot, ClickType clickType) {
        Object object = mappedInventory.get(slot);
        if (!UserInterfaceUtils.isClickable(object)) return;
        if (object instanceof UIButton) {
            Clickable button = (UIButton) object;
            switch (clickType) {
                case RIGHT -> buttonHandler(button.rightClick(), slot);
                case LEFT -> buttonHandler(button.leftClick(), slot);
                case MIDDLE -> buttonHandler(button.middleClick(), slot);
            }
            return;
        }
    }

    private void buttonHandler(ClickActions action, int slot) {
        switch (action) {
            case EXIT, CLOSE -> close();
            case BACK -> {
                UserInterface ui = new StorePageUI(player, backPage);
                ui.update();
                ui.open();
            }
            case ADD -> {
                UIButton button = (UIButton) mappedInventory.get(slot);
                // TODO: Max stack size
                if (button.getAmount() + selectedItem.getAmount() <= 64) {
                    selectedItem.setAmount(selectedItem.getAmount() + button.getAmount());
                }
                updateUISlots(true);
            }
            case REMOVE -> {
                UIButton button = (UIButton) mappedInventory.get(slot);
                if (selectedItem.getAmount() - button.getAmount() > 0) {
                    selectedItem.setAmount(selectedItem.getAmount() - button.getAmount());
                }
                updateUISlots(true);
            }
            case SET -> {
                UIButton button = (UIButton) mappedInventory.get(slot);
                selectedItem.setAmount(button.getAmount());
                updateUISlots(true);
            }
            case SELL -> {
                // TODO: Add selling implementation
                UserInterface ui = new StorePageUI(player, backPage);
                ui.update();
                ui.open();
            }
        }
    }
}
