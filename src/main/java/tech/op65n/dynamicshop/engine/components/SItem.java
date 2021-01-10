package tech.op65n.dynamicshop.engine.components;

import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SItem {

    private int ID;

    private int catID;

    private EItemType type = EItemType.MATERIAL;

    private String item = "ACACIA_BOAT";

    private SItemPricing itemPricing = new SItemPricing();

    private SItemMeta metadata = new SItemMeta();

}
