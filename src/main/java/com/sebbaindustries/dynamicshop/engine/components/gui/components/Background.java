package com.sebbaindustries.dynamicshop.engine.components.gui.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Background {

    private Material material;
    private String display;
    private List<String> lore = new ArrayList<>();

}
