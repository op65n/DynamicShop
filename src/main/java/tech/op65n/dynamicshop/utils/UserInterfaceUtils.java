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

    public static ItemStack fallback() {
        ItemStack iStack = new ItemStack(Material.STRUCTURE_VOID);
        iStack.getItemMeta().setDisplayName(Color.format("&4&lError"));
        iStack.getItemMeta().setLore(Collections.singletonList(Color.format("&cPlease check the configuration!")));
        return iStack;
    }

    public static ItemStack getBukkitItemStack(BukkitItemStack bukkitItemStack) {
        ItemStack iStack = null;

        if (bukkitItemStack.material().getKey() == EItemType.BASE64) {
            iStack = new ItemStack(ItemStack.deserializeBytes(Base64.getDecoder().decode(bukkitItemStack.material().getRight())));
        }

        if (bukkitItemStack.material().getKey() == EItemType.TEXTURE) {
            iStack = SkullHelper.getCustomSkull64(bukkitItemStack.material().getRight());
        }

        if (bukkitItemStack.material().getKey() == EItemType.MATERIAL) {
            Material material = Material.matchMaterial(bukkitItemStack.material().getRight());
            if (material == null) return fallback();
            iStack = new ItemStack(material);
        }

        if (iStack == null) return fallback();

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
                inventory.setItem(slot, UserInterfaceUtils.getBukkitItemStack((BukkitItemStack) item));
            }
        });
    }

    public static SItem applyItemPlaceholders(SItem shopItem, DisplayItem displayItem) {
        shopItem.getMetadata().setDisplay(Color.format(displayItem.getDisplay()));
        shopItem.getMetadata().setLore(displayItem.getColoredLore());

        //String display = displayItem.getDisplay();
        //display = display.replace("%item%", shopItem.getItem());
        //shopItem.setDisplay(display);

        if (shopItem.getMetadata().getLore() != null && !shopItem.getMetadata().getLore().isEmpty()) {
            List<String> lore = shopItem.getMetadata().getLore().stream()
                    .map(l4e -> l4e.replace("%buy_price%", String.valueOf(shopItem.getItemPricing().getPriceBuy() * shopItem.amount())))
                    .map(l4e -> l4e.replace("%buy_price_single%", String.valueOf(shopItem.getItemPricing().getPriceBuy())))
                    .map(l4e -> l4e.replace("%sell_price%", String.valueOf(shopItem.getItemPricing().getPriceSell() * shopItem.amount())))
                    .map(l4e -> l4e.replace("%sell_price_single%", String.valueOf(shopItem.getItemPricing().getPriceSell())))
                    .map(l4e -> l4e.replace("%currency%", "€"))
                    .map(Color::format)
                    .collect(Collectors.toList());
            shopItem.getMetadata().setLore(lore);
        }

        return shopItem;
    }

    public static void mapButtons(Map<Integer, Object> mappedInventory, BaseUI cache, SItem selectedItem) {
        cache.buttons().forEach(button -> {
            if (button.getOnClick() == ClickActions.SET && button.getAmount() == selectedItem.getAmount()) return;
            // TODO: Max stack size
            if (button.getOnClick() == ClickActions.ADD && button.getAmount() + selectedItem.getAmount() > 64)
                return;
            if (button.getOnClick() == ClickActions.REMOVE && selectedItem.getAmount() - button.getAmount() < 1) return;
            mappedInventory.put(button.getSlot(), button);
        });
    }

    public static void mapItem(Map<Integer, Object> mappedInventory, SItem selectedItem, DisplayItem displayItem) {
        applyItemPlaceholders(selectedItem, displayItem);
        mappedInventory.put(displayItem.getSlot(), selectedItem);
    }

}
