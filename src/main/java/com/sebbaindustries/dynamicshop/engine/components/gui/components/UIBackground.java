package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import com.sebbaindustries.dynamicshop.engine.components.gui.interfaces.BukkitItemStack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UIBackground implements BukkitItemStack {

    private Material material;
    private String display;
    private List<String> lore = new ArrayList<>();

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
}
