package tech.op65n.dynamicshop.engine.structure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemStruct {

    private Integer priority = 1000;

    private String material = "ACACIA_BOAT";

    private String texture;

    private String base64;

    private Double price_buy = 0.00;

    private Double price_sell = 0.00;

    private String display;

    private List<String> lore;

    private Boolean flatline = false;

    private String command;

}
