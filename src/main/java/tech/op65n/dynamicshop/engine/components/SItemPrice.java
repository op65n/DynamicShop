package tech.op65n.dynamicshop.engine.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;

@Getter
@Setter
@NoArgsConstructor
public class SItemPrice {

    private Double priceBuy = 0.00D;

    private Double priceSell = 0.00D;

    private int buys = 0;

    private int sells = 0;

    private boolean flatline = true;

    private Double cnfPriceBuy = 0.00D;

    private Double cnfPriceSell = 0.00D;

    public SItemPrice(ItemStruct struct) {
        this.flatline = struct.getFlatline();
        this.cnfPriceBuy = struct.getPrice_buy();
        this.cnfPriceSell = struct.getPrice_sell();
    }

}
