package com.sebbaindustries.dynamicshop.engine.components.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Icon {

    private Material material = Material.ACACIA_BOAT;
    private String display;
    private List<String> lore = new ArrayList<>();
    private int amount = 1;

}
