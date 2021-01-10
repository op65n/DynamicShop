package tech.op65n.dynamicshop.utils;

import tech.op65n.dynamicshop.engine.components.SItem;
import tech.op65n.dynamicshop.engine.ui.components.DisplayItem;
import tech.op65n.dynamicshop.engine.ui.components.UIBackground;
import tech.op65n.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import tech.op65n.dynamicshop.engine.ui.interfaces.Clickable;
import tech.op65n.dynamicshop.helpers.SkullHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserInterfaceUtils {


    public static ItemStack getBukkitItemStack(BukkitItemStack bukkitItemStack) {
        ItemStack iStack = null;

        if (bukkitItemStack.base64() != null) {
            iStack = new ItemStack(ItemStack.deserializeBytes(bukkitItemStack.base64()));
        }

        if (bukkitItemStack.texture() != null) {
            iStack = SkullHelper.getCustomSkull64(bukkitItemStack.texture());
        }

        if (iStack == null) {
            iStack = new ItemStack(bukkitItemStack.material());
        }

        iStack.setAmount(bukkitItemStack.amount());
        ItemMeta iMeta = iStack.getItemMeta();

        /*
        Item lore
         */
        if (bukkitItemStack.lore() != null && !bukkitItemStack.lore().isEmpty()) {
            List<String> coloredLore = new ArrayList<>();
            bukkitItemStack.lore().forEach(loreLine -> coloredLore.add(Color.format(loreLine)));
            iMeta.setLore(coloredLore);
        }

        /*
        Item display name
         */
        if (bukkitItemStack.display() != null) iMeta.setDisplayName(Color.format(bukkitItemStack.display()));

        iStack.setItemMeta(iMeta);
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
                player.getOpenInventory().setItem(slot, getBukkitItemStack(bukkitItemStack));
            }
        });
        player.updateInventory();
    }

    public static void setupInventory(Inventory inventory, final Map<Integer, Object> mappedInventory, final int cache) {
        mappedInventory.forEach((slot, item) -> {
            if (slot > cache * 9 - 1) return;
            if (item instanceof BukkitItemStack) {
                BukkitItemStack bukkitItemStack = (BukkitItemStack) item;
                inventory.setItem(slot, UserInterfaceUtils.getBukkitItemStack(bukkitItemStack));
            }
        });
    }

    public static SItem applyItemPlaceholders(SItem shopItem, DisplayItem displayItem) {
        //shopItem.setDisplay(Color.format(displayItem.getDisplay()));
        //shopItem.setLore(displayItem.getColoredLore());
//
        //String display = shopItem.getDisplay();
        //display = display.replace("%item%", shopItem.getItem());
        //shopItem.setDisplay(display);
//
        //if (shopItem.getLore() != null && !shopItem.getLore().isEmpty()) {
        //    //List<String> lore = shopItem.getLore().stream()
        //    //        .map(l4e -> l4e.replace("%buy_price%", String.valueOf(shopItem.getBuyPrice() * shopItem.amount())))
        //    //        .map(l4e -> l4e.replace("%buy_price_single%", String.valueOf(shopItem.getBuyPrice())))
        //    //        .map(l4e -> l4e.replace("%sell_price%", String.valueOf(shopItem.getSellPrice() * shopItem.amount())))
        //    //        .map(l4e -> l4e.replace("%sell_price_single%", String.valueOf(shopItem.getSellPrice())))
        //    //        .map(l4e -> l4e.replace("%currency%", "€"))
        //    //        .map(Color::format)
        //    //        .collect(Collectors.toList());
        //    //shopItem.setLore(lore);
        //}

        return shopItem;
    }

    //public static void mapButtons(Map<Integer, Object> mappedInventory, BaseUI cache, ShopItemOld selectedItem) {
    //    cache.buttons().forEach(button -> {
    //        if (button.getOnClick() == ClickActions.SET && button.getAmount() == selectedItem.getAmount()) return;
    //        if (button.getOnClick() == ClickActions.ADD && button.getAmount() + selectedItem.getAmount() > selectedItem.getMaterial().getMaxStackSize())
    //            return;
    //        if (button.getOnClick() == ClickActions.REMOVE && selectedItem.getAmount() - button.getAmount() < 1) return;
    //        mappedInventory.put(button.getSlot(), button);
    //    });
    //}

    //public static void mapItem(Map<Integer, Object> mappedInventory, ShopItemOld selectedItem, DisplayItem displayItem) {
    //    applyItemPlaceholders(selectedItem, displayItem);
    //    mappedInventory.put(displayItem.getSlot(), selectedItem);
    //}

}
