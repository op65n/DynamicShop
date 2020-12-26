package com.sebbaindustries.dynamicshop.engine.components.gui.cache;

import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIBackground;
import com.sebbaindustries.dynamicshop.engine.components.gui.components.UIButton;
import com.sebbaindustries.dynamicshop.utils.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SellPageUICache {

    private String name = "$NULL";
    private int size = 6;

    private DisplayItem item;

    private UIBackground background;
    private List<UIButton> button = new ArrayList<>();

    @Getter
    @Setter
    public class DisplayItem {
        private String display = "%item%";
        private List<String> lore;
        private int slot;

        public List<String> getColoredLore() {
            if (lore == null) return null;
            List<String> coloredLore = new ArrayList<>();
            lore.forEach(lore -> coloredLore.add(Color.format(lore)));
            return coloredLore;
        }
    }

}
