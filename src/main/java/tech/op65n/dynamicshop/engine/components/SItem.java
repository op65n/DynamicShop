package tech.op65n.dynamicshop.engine.components;

import org.apache.commons.lang3.tuple.Pair;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private EItemType type = EItemType.MATERIAL;

    private String item = "ACACIA_BOAT";

    private SItemPricing itemPricing = new SItemPricing();

    private SItemMeta metadata = new SItemMeta();

    private ClickActions onClick = ClickActions.NA;
    private ClickActions onRightClick = ClickActions.NA;
    private ClickActions onLeftClick = ClickActions.NA;
    private ClickActions onMiddleClick = ClickActions.NA;

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
