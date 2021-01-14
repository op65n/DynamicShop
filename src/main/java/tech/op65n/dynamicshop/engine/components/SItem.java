package tech.op65n.dynamicshop.engine.components;

import org.apache.commons.lang3.tuple.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import tech.op65n.dynamicshop.engine.ui.components.ClickActions;
import tech.op65n.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import tech.op65n.dynamicshop.engine.ui.interfaces.Clickable;
import tech.op65n.dynamicshop.utils.ShopUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SItem implements BukkitItemStack, Clickable {

    private int amount = 1;

    private int ID;

    private int catID;

    private String categoryFilename;

    private EItemType type = EItemType.MATERIAL;

    private String item = "ACACIA_BOAT";

    private SItemPricing itemPricing = new SItemPricing();

    private SItemMeta metadata = new SItemMeta();

    private ClickActions onClick = ClickActions.NA;
    private ClickActions onRightClick = ClickActions.NA;
    private ClickActions onLeftClick = ClickActions.NA;
    private ClickActions onMiddleClick = ClickActions.NA;

    public SItem(ItemStruct struct, String categoryFilename) {
        var pair = ShopUtils.getTypeAndItemPair(struct.getMaterial(), struct.getTexture(), struct.getBase64());
        this.type = pair.getLeft();
        this.item = pair.getRight();
        this.categoryFilename = categoryFilename;

        this.metadata.setDisplay(struct.getDisplay());
        this.metadata.setPriority(struct.getPriority());
        this.metadata.setLore(struct.getLore());
        this.metadata.setCommand(struct.getCommand());
    }

    @Override
    public int amount() {
        return amount;
    }

    @Override
    public Pair<EItemType, String> material() {
        return Pair.of(type, item);
    }

    @Override
    public String display() {
        return metadata.getDisplay();
    }

    @Override
    public List<String> lore() {
        return metadata.getLore();
    }

    @Override
    public ClickActions rightClick() {
        if (this.onRightClick == ClickActions.NA) return this.onClick;
        return this.onRightClick;
    }

    @Override
    public ClickActions leftClick() {
        if (this.onLeftClick == ClickActions.NA) return this.onClick;
        return this.onLeftClick;
    }

    @Override
    public ClickActions middleClick() {
        if (this.onMiddleClick == ClickActions.NA) return this.onClick;
        return this.onMiddleClick;
    }

}
