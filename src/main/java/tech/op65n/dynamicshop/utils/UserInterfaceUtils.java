package tech.op65n.dynamicshop.utils;

import org.bukkit.Material;
import tech.op65n.dynamicshop.engine.components.EItemType;
import tech.op65n.dynamicshop.engine.components.SItem;
import tech.op65n.dynamicshop.engine.ui.components.ClickActions;
import tech.op65n.dynamicshop.engine.ui.components.DisplayItem;
import tech.op65n.dynamicshop.engine.ui.components.UIBackground;
import tech.op65n.dynamicshop.engine.ui.interfaces.BaseUI;
import tech.op65n.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import tech.op65n.dynamicshop.engine.ui.interfaces.Clickable;
import tech.op65n.dynamicshop.helpers.SkullHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class UserInterfaceUtils {

    /**
     * Fallback material when serialized material from database or configuration does not exists
     *
     * @see Material
     * @return Fallback material Material.STRUCTURE_VOID with modified display name and lore.
     */
    public static ItemStack fallbackItem() {
        ItemStack iStack = new ItemStack(Material.STRUCTURE_VOID);
        iStack.getItemMeta().setDisplayName(Color.format("&4&lError while loading item"));
        iStack.getItemMeta().setLore(Collections.singletonList(Color.format("&cGreat, you fucked up, now please check the configuration!")));
        return iStack;
    }

    public static boolean isClickable(Object object) {
        return object instanceof Clickable;
    }

    public static void createBackground(Map<Integer, Object> mappedInventory, UIBackground background, final int cache) {
        if (background != null && background.getMaterial() != null) {
            for (int i = 0; i < cache * 9; i++) {
                mappedInventory.put(i, background);
            }
        }
    }

    public static void clearUI(Player player) {
        player.getOpenInventory().getTopInventory().clear();
    }

    public static int calculateInventorySize(final Map<Integer, Object> mappedInventory) {
        int newSize = 0;
        for (Map.Entry<Integer, Object> entry : mappedInventory.entrySet()) {
            if (entry.getValue() instanceof UIBackground) continue;
            if (newSize < entry.getKey()) newSize = entry.getKey();
        }

        newSize = (int) Math.ceil((double) newSize / 9.0);
        return newSize;
    }

    public static void fillInventory(final Player player, final Map<Integer, Object> mappedInventory, final int cache) {
        mappedInventory.forEach((slot, item) -> {
            if (slot > cache * 9 - 1) return;
            if (item instanceof BukkitItemStack) {
                BukkitItemStack bukkitItemStack = (BukkitItemStack) item;
                player.getOpenInventory().setItem(slot, bukkitItemStack.toItemStack());
            }
        });
        player.updateInventory();
    }

    public static void setupInventory(Inventory inventory, final Map<Integer, Object> mappedInventory, final int cache) {
        mappedInventory.forEach((slot, item) -> {
            if (slot > cache * 9 - 1) return;
            if (item instanceof BukkitItemStack) {
                inventory.setItem(slot, ((BukkitItemStack) item).toItemStack());
            }
        });
    }

    public static void applyItemPlaceholders(SItem shopItem, DisplayItem displayItem) {

        shopItem.getMetadata().setLore(displayItem.getColoredLore());

        String display = displayItem.getDisplay();
        display = display.replace("%item%", shopItem.toItemStack().getItemMeta().getDisplayName());
        shopItem.getMetadata().setDisplay(display);


        if (shopItem.getMetadata().getLore() != null && !shopItem.getMetadata().getLore().isEmpty()) {
            List<String> lore = shopItem.getMetadata().getLore().stream()
                    .map(l4e -> l4e.replace("%buy_price%", String.valueOf(shopItem.getItemPricing().getPriceBuy() * shopItem.amount())))
                    .map(l4e -> l4e.replace("%buy_price_single%", String.valueOf(shopItem.getItemPricing().getPriceBuy())))
                    .map(l4e -> l4e.replace("%sell_price%", String.valueOf(shopItem.getItemPricing().getPriceSell() * shopItem.amount())))
                    .map(l4e -> l4e.replace("%sell_price_single%", String.valueOf(shopItem.getItemPricing().getPriceSell())))
                    .map(l4e -> l4e.replace("%currency%", "€"))
                    .map(l4e -> l4e.replace("%base%", String.valueOf(shopItem.getItemPricing().calcBuyBase(shopItem.amount()))))
                    .map(Color::format)
                    .collect(Collectors.toList());
            shopItem.getMetadata().setLore(lore);
        }

    }

    public static void mapButtons(Map<Integer, Object> mappedInventory, BaseUI cache, SItem selectedItem) {
        cache.buttons().forEach(button -> {
            if (button.getOnClick() == ClickActions.SET && button.getAmount() == selectedItem.getAmount()) return;
            if (button.getOnClick() == ClickActions.ADD && button.getAmount() + selectedItem.getAmount() > 64) return;
            if (button.getOnClick() == ClickActions.REMOVE && selectedItem.getAmount() - button.getAmount() < 1) return;
            mappedInventory.put(button.getSlot(), button);
        });
    }

    public static void mapItem(Map<Integer, Object> mappedInventory, SItem selectedItem, DisplayItem displayItem) {
        applyItemPlaceholders(selectedItem, displayItem);
        mappedInventory.put(displayItem.getSlot(), selectedItem);
    }

}
