package tech.op65n.dynamicshop.engine.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import tech.op65n.dynamicshop.engine.structure.ShopCategoryStruct;
import tech.op65n.dynamicshop.utils.ShopUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
public class SCategory {

    private Integer ID;

    private Integer priority;

    private String filename;

    private String name;

    private SIcon icon;

    private Map<Integer, SItem> items = new TreeMap<>();

    public SCategory(ShopCategoryStruct struct) {
        this.priority = struct.getPriority();
        this.filename = struct.getFilename();
        this.name = struct.getName();
        this.icon = new SIcon(struct.getIcon());
        int cnt = 0;
        for (ItemStruct iStruct : struct.getItem()) {
            items.put(cnt, new SItem(iStruct, struct.getFilename()));
            cnt++;
        }
    }

}
