package tech.op65n.dynamicshop.engine.ui.components;

import tech.op65n.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UIBackground implements BukkitItemStack {

    private Material material;
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
    public Material material() {
        return this.material;
    }

    @Override
    public String display() {
        return this.display;
    }

    @Override
    public List<String> lore() {
        return this.lore;
    }

    @Override
    public String texture() {
        return this.texture;
    }

    @Override
    public byte[] base64() {
        if (base64 == null) return null;
        return Base64.getDecoder().decode(this.base64);
    }
}
