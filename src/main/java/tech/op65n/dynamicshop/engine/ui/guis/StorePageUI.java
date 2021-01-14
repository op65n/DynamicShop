package tech.op65n.dynamicshop.engine.ui.guis;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.components.SCategory;
import tech.op65n.dynamicshop.engine.components.SItem;
import tech.op65n.dynamicshop.engine.ui.cache.InventoryHolderCache;
import tech.op65n.dynamicshop.engine.ui.cache.StorePageUICache;
import tech.op65n.dynamicshop.engine.ui.components.ClickActions;
import tech.op65n.dynamicshop.engine.ui.components.UIButton;
import tech.op65n.dynamicshop.engine.ui.interfaces.Clickable;
import tech.op65n.dynamicshop.engine.ui.interfaces.UserInterface;
import tech.op65n.dynamicshop.utils.Color;
import tech.op65n.dynamicshop.utils.ListUtils;
import tech.op65n.dynamicshop.utils.ObjectUtils;
import tech.op65n.dynamicshop.utils.UserInterfaceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

        inventory = Bukkit.createInventory(null, cache.size() * 9, Color.format(cache.name().replace("%category%", category.getName())));
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
    Blackbox no touche
     */
    private void createItemsPanel() {
        int cornerA = cache.getItems().getCornerA();
        int cornerB = cache.getItems().getCornerB();

        int columnStart = (cornerA + 1) / 9;
        int columnEnd = (cornerB + 1) / 9;

        int columnLength = Math.abs(columnStart - columnEnd) + 1;
        int rowLength = Math.abs(cornerA - (cornerB - ((columnLength - 1) * 9))) + 1;

        ListUtils<SItem> listUtils = new ListUtils<>(new ArrayList<>(category.getItems().values()));

        int itemCount = 0;

        for (int y = 0; y < columnEnd; y++) {
            for (int x = cornerA; x < rowLength + cornerA; x++) {
                SItem item = listUtils.getNext();
                if (item == null) continue;
                item.setOnLeftClick(cache.getItems().getOnLeftClick());
                item.setOnRightClick(cache.getItems().getOnRightClick());
                item.setOnMiddleClick(cache.getItems().getOnMiddleClick());
                item.setOnClick(cache.getItems().getOnClick());
                applyLoreHolders(item);
                mappedInventory.put(x + (y * 9), item);
                itemCount++;
            }
        }

        int usedColumns = (int) Math.ceil((double) itemCount / 9.0);

        if (usedColumns >= columnLength) return;
        if (cache.getItems().isCollapsed()) collapseBy = (columnLength - usedColumns) * 9;

    }

    private void applyLoreHolders(SItem item) {
        System.out.println(ObjectUtils.deserializeObjectToString(item));
        if (item.getMetadata().getLore() == null || item.getMetadata().getLore().isEmpty()) {
            if (cache.getItems().getLore() == null) return;
            List<String> lore = cache.getItems().getLore().stream()
                    .map(l4e -> l4e.replace("%price_buy_single%", String.valueOf(item.getItemPricing().getPriceBuy())))
                    .map(l4e -> l4e.replace("%price_buy_stack%", String.valueOf(item.getItemPricing().getPriceBuy() * 64)))
                    .map(l4e -> l4e.replace("%price_sell_single%", String.valueOf(item.getItemPricing().getPriceSell())))
                    .map(l4e -> l4e.replace("%price_sell_stack%", String.valueOf(item.getItemPricing().getPriceSell() * 64)))
                    .map(l4e -> l4e.replace("%currency%", "€"))
                    .map(Color::format)
                    .collect(Collectors.toList());
            item.getMetadata().setLore(lore);
            return;
        }
        List<String> lore = item.getMetadata().getLore().stream()
                .map(l4e -> l4e.replace("%price_buy_single%", String.valueOf(item.getItemPricing().getPriceBuy())))
                .map(l4e -> l4e.replace("%price_buy_stack%", String.valueOf(item.getItemPricing().getPriceBuy() * 64)))
                .map(l4e -> l4e.replace("%price_sell_single%", String.valueOf(item.getItemPricing().getPriceSell())))
                .map(l4e -> l4e.replace("%price_sell_stack%", String.valueOf(item.getItemPricing().getPriceSell() * 64)))
                .map(l4e -> l4e.replace("%currency%", "€"))
                .map(Color::format)
                .collect(Collectors.toList());
        item.getMetadata().setLore(lore);
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
                case RIGHT -> buttonHandler(button.rightClick());
                case LEFT -> buttonHandler(button.leftClick());
                case MIDDLE -> buttonHandler(button.middleClick());
            }
            return;
        }
        if (object instanceof SItem) {
            Clickable item = (SItem) object;
            switch (clickType) {
                case RIGHT -> itemHandler(item.rightClick(), slot);
                case LEFT -> itemHandler(item.leftClick(), slot);
                case MIDDLE -> itemHandler(item.middleClick(), slot);
            }
        }
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
                UserInterface ui = new SellPageUI(player, (SItem) this.mappedInventory.get(slot), category);
                ui.update();
                ui.open();
            }
            case BUY -> {
                UserInterface ui = new BuyPageUI(player, (SItem) this.mappedInventory.get(slot), category);
                ui.update();
                ui.open();
            }
        }
    }
}
