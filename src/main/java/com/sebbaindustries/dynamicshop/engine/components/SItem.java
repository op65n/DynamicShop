package com.sebbaindustries.dynamicshop.engine.components;

import com.sebbaindustries.dynamicshop.engine.structure.ItemStruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SItem {

    private EItemType type = EItemType.MATERIAL;

    private String item = "ACACIA_BOAT";

    private SItemPricing itemPricing = new SItemPricing();

    private SItemMeta metadata = new SItemMeta();

    public SItem(ItemStruct struct) {
        if (struct.getMaterial() != null) {
            item = struct.getMaterial();
            type = EItemType.MATERIAL;
        }
        if (struct.getTexture() != null) {
            item = struct.getTexture();
            type = EItemType.TEXTURE;
        }
        if (struct.getBase64() != null) {
            item = struct.getBase64();
            type = EItemType.BASE64;
        }

        itemPricing.setPriceBuy(struct.getPrice_buy());
        itemPricing.setPriceSell(struct.getPrice_sell());
        itemPricing.setFlatline(struct.getFlatline());

        metadata.setPriority(struct.getPriority());
        metadata.setDisplay(struct.getDisplay());
        metadata.setLore(struct.getLore());
        metadata.setCommand(struct.getCommand());

    }

}
