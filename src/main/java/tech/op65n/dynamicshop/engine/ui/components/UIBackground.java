package tech.op65n.dynamicshop.engine.ui.components;

import org.apache.commons.lang3.tuple.Pair;
import tech.op65n.dynamicshop.engine.components.EItemType;
import tech.op65n.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import tech.op65n.dynamicshop.utils.ShopUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UIBackground implements BukkitItemStack {

    private String material;
    private String display;
    private List<String> lore = new ArrayList<>();
    private int amount = 1;
    private String texture = null;
    private String base64 = null;

    @Override
    public int amount() {
        return this.amount;
    }

    @Override
    public Pair<EItemType, String> material() {
        return ShopUtils.getTypeAndItemPair(material, texture, base64);
    }


    @Override
    public String display() {
        return this.display;
    }

    @Override
    public List<String> lore() {
        return this.lore;
    }
}
