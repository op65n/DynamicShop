package tech.op65n.dynamicshop.engine.structure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemStruct {

    private int priority = 1000;

    private String material = "ACACIA_BOAT";

    private String texture;

    private String base64;

    private Double price_buy = null;

    private Double price_sell = null;

    private String display;

    private List<String> lore;

    private boolean flatline = false;

    private String command;

}
