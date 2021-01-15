package tech.op65n.dynamicshop.engine.components;

import lombok.Getter;
import lombok.Setter;
import tech.op65n.dynamicshop.engine.structure.ShopCategoryStruct;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SCategory {

    private int ID = -1;

    private int priority = 1000;

    private String filename;

    private String name;

    private SIcon icon;

    private List<SItem> items = new ArrayList<>();

    public SCategory(ShopCategoryStruct struct) {
        this.priority = struct.getPriority();
        this.filename = struct.getFilename();
        this.name = struct.getName();
        this.icon = new SIcon(struct.getIcon());
        struct.getItem().forEach(struct1 -> items.add(new SItem(struct1, struct.getFilename())));
    }

}
