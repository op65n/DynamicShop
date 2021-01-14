package tech.op65n.dynamicshop.engine.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.op65n.dynamicshop.engine.structure.IconStruct;
import tech.op65n.dynamicshop.utils.ShopUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SIcon {

    private EItemType type = EItemType.MATERIAL;

    private String item = "ACACIA_BOAT";

    private String display;

    private List<String> lore;

    public SIcon(IconStruct struct) {
        var pair = ShopUtils.getTypeAndItemPair(struct.getMaterial(), struct.getTexture(), struct.getBase64());
        this.type = pair.getLeft();
        this.item = pair.getRight();
        this.display = struct.getDisplay();
        this.lore = struct.getLore();
    }

}
