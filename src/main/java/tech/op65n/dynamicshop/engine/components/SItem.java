package tech.op65n.dynamicshop.engine.components;

import org.apache.commons.lang3.tuple.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.op65n.dynamicshop.database.tables.TableItem;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import tech.op65n.dynamicshop.engine.ui.components.ClickActions;
import tech.op65n.dynamicshop.engine.ui.interfaces.BukkitItemStack;
import tech.op65n.dynamicshop.engine.ui.interfaces.Clickable;
import tech.op65n.dynamicshop.utils.ShopUtils;

import java.util.List;
import java.util.Objects;

public class SItem implements BukkitItemStack, Clickable {

    private int amount = 1;

    private int ID = -1;

    private int catID;

    private boolean needsDBUpdate = false;

    private String categoryFilename;

    private EItemType type = EItemType.MATERIAL;

    private String item = "ACACIA_BOAT";

    private SItemPrice itemPricing = new SItemPrice();

    private SItemHistory itemHistory = new SItemHistory(null);

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

        this.metadata = new SItemMeta(struct);
        this.itemPricing = new SItemPrice(struct);
    }

    public void applyDBWrapper(TableItem.Holder holder) {
        this.ID = holder.getId();
        this.catID = holder.getCategoryID();
        this.itemPricing.setBuys(holder.getBuys());
        this.itemPricing.setSells(holder.getSells());

        // Buy price
        if (!Objects.equals(itemPricing.getCnfPriceBuy(), holder.getCnfPriceBuy())) {
            itemPricing.setPriceBuy(itemPricing.getCnfPriceBuy());
            this.needsDBUpdate = true;
        }
        if (Objects.equals(itemPricing.getCnfPriceBuy(), holder.getCnfPriceBuy())) {
            itemPricing.setPriceBuy(holder.getPriceBuy());
        }

        // Sell price
        if (!Objects.equals(itemPricing.getCnfPriceSell(), holder.getCnfPriceSell())) {
            itemPricing.setPriceSell(itemPricing.getCnfPriceSell());
            this.needsDBUpdate = true;
        }
        if (Objects.equals(itemPricing.getCnfPriceSell(), holder.getCnfPriceSell())) {
            itemPricing.setPriceSell(holder.getPriceSell());
        }
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
