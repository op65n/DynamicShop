package tech.op65n.dynamicshop.engine.ui.interfaces;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tech.op65n.dynamicshop.engine.components.EItemType;
import tech.op65n.dynamicshop.helpers.SkullHelper;
import tech.op65n.dynamicshop.utils.Color;
import tech.op65n.dynamicshop.utils.UserInterfaceUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public interface BukkitItemStack {

    int amount();

    Pair<EItemType, String> material();

    String display();

    List<String> lore();

    default ItemStack toItemStack() {
        ItemStack iStack = null;

        if (this.material().getKey() == EItemType.BASE64) {
            iStack = new ItemStack(ItemStack.deserializeBytes(Base64.getDecoder().decode(this.material().getRight())));
        }

        if (this.material().getKey() == EItemType.TEXTURE) {
            iStack = SkullHelper.getCustomSkull64(this.material().getRight());
        }

        if (this.material().getKey() == EItemType.MATERIAL) {
            Material material = Material.matchMaterial(this.material().getRight());
            if (material == null) return UserInterfaceUtils.fallbackItem();
            iStack = new ItemStack(material);
        }

        if (iStack == null) return UserInterfaceUtils.fallbackItem();

        iStack.setAmount(this.amount());
        ItemMeta iMeta = iStack.getItemMeta();

        /*
        Item display name
         */
        if (this.display() != null) iMeta.setDisplayName(Color.format(this.display()));
        if (this.display() == null) iMeta.setDisplayName(iStack.getI18NDisplayName());


        /*
        Item lore
         */
        if (this.lore() != null && !this.lore().isEmpty()) {
            List<String> coloredLore = new ArrayList<>();
            this.lore().forEach(loreLine -> coloredLore.add(Color.format(loreLine)));
            iMeta.setLore(coloredLore);
        }

        iStack.setItemMeta(iMeta);
        return iStack;
    }

}
