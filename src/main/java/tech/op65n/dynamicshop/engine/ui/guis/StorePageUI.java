package tech.op65n.dynamicshop.engine.ui.guis;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.ui.cache.InventoryHolderCache;
import tech.op65n.dynamicshop.engine.ui.cache.StorePageUICache;
import tech.op65n.dynamicshop.engine.ui.components.ClickActions;
import tech.op65n.dynamicshop.engine.ui.interfaces.UserInterface;
import tech.op65n.dynamicshop.utils.Color;
import tech.op65n.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.TreeMap;

public class StorePageUI implements UserInterface {

    private final Player player;
    private final StorePageUICache cache;
    private final SCategory category;
    private final Map<Integer, Object> mappedInventory = new TreeMap<>();
    int collapseBy = 0;
    private Inventory inventory;

    public StorePageUI(Player player, SCategory category) {
        this.player = player;
        this.cache = Core.gCore().getEngine().ui().getStorePageCache();
        this.category = category;

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

        createItemsPanel();

        cache.setSize(UserInterfaceUtils.calculateInventorySize(mappedInventory));

        inventory = Bukkit.createInventory(null, cache.size() * 9, Color.format(cache.name()));
        UserInterfaceUtils.setupInventory(inventory, mappedInventory, cache.size());

        InventoryHolderCache.cache(player, this);
    }

    @Override
    public void updateUISlots(boolean updateCurrent) {
        /*
        background
         */
        UserInterfaceUtils.createBackground(mappedInventory, cache.background(), cache.size());

        /*
        panel
         */
        createItemsPanel();

        /*
        buttons
         */
        cache.buttons().forEach(button -> {
            if (button.getSlot() > 26) {
                button.setSlot(button.getSlot() - collapseBy);
            }
            mappedInventory.put(button.getSlot(), button);
        });

        if (!updateCurrent) return;
        UserInterfaceUtils.clearUI(player);
        UserInterfaceUtils.fillInventory(player, mappedInventory, cache.size());
        player.updateInventory();
    }

    /*
    Blackbox
     */
    private void createItemsPanel() {
        //int cornerA = cache.getItems().getCornerA();
        //int cornerB = cache.getItems().getCornerB();
//
        //int columnStart = (cornerA + 1) / 9;
        //int columnEnd = (cornerB + 1) / 9;
//
        //int columnLength = Math.abs(columnStart - columnEnd) + 1;
        //int rowLength = Math.abs(cornerA - (cornerB - ((columnLength - 1) * 9))) + 1;
//
        //ListUtils<ShopItemOld> listUtils = new ListUtils<>(category.getOrderedItemList());
//
        //int itemCount = 0;
//
        //for (int y = 0; y < columnEnd; y++) {
        //    for (int x = cornerA; x < rowLength + cornerA; x++) {
        //        ShopItemOld item = listUtils.getNext();
        //        if (item == null) continue;
        //        item.setOnLeftClick(cache.getItems().getOnLeftClick());
        //        item.setOnRightClick(cache.getItems().getOnRightClick());
        //        item.setOnMiddleClick(cache.getItems().getOnMiddleClick());
        //        item.setOnClick(cache.getItems().getOnClick());
        //        mappedInventory.put(x + (y * 9), item);
        //        itemCount++;
        //    }
        //}
//
        //int usedColumns = (int) Math.ceil((double) itemCount / 9.0);
//
        //if (usedColumns >= columnLength) return;
        //if (cache.getItems().isCollapsed()) collapseBy = (columnLength - usedColumns) * 9;

    }


    @Override
    public void close() {
        player.closeInventory();
        InventoryHolderCache.removeIfPresent(player);
    }

    @Override
    public void onClick(int slot, ClickType clickType) {
        //Object object = mappedInventory.get(slot);
        //if (!UserInterfaceUtils.isClickable(object)) return;
        //if (object instanceof UIButton) {
        //    Clickable button = (UIButton) object;
        //    switch (clickType) {
        //        case RIGHT -> buttonHandler(button.rightClick());
        //        case LEFT -> buttonHandler(button.leftClick());
        //        case MIDDLE -> buttonHandler(button.middleClick());
        //    }
        //    return;
        //}
        //if (object instanceof ShopItemOld) {
        //    Clickable item = (ShopItemOld) object;
        //    switch (clickType) {
        //        case RIGHT -> itemHandler(item.rightClick(), slot);
        //        case LEFT -> itemHandler(item.leftClick(), slot);
        //        case MIDDLE -> itemHandler(item.middleClick(), slot);
        //    }
        //}
    }

    private void buttonHandler(ClickActions action) {
        switch (action) {
            case EXIT, CLOSE -> close();
            case BACK -> {
                UserInterface ui = new MainPageUI(player);
                ui.update();
                ui.open();
            }
        }
    }

    private void itemHandler(ClickActions action, int slot) {
        switch (action) {
            case SELL -> {
                //UserInterface ui = new SellPageUI(player, (ShopItemOld) this.mappedInventory.get(slot), category);
                //ui.update();
                //ui.open();
            }
            case BUY -> {
                //UserInterface ui = new BuyPageUI(player, (ShopItemOld) this.mappedInventory.get(slot), category);
                //ui.update();
                //ui.open();
            }
        }
    }
}
