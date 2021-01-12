package tech.op65n.dynamicshop.engine.structure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShopCategoryStruct {

    private String filename;

    private String name;

    private Integer priority = 1000;

    private IconStruct icon = new IconStruct();

    private List<ItemStruct> item;

}
