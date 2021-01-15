package tech.op65n.dynamicshop.engine.components;

import tech.op65n.dynamicshop.engine.structure.ItemStruct;
import tech.op65n.dynamicshop.utils.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@Setter
public class SItemMeta {

    private Integer priority;

    private String display;

    private List<String> lore;

    @Getter
    private String command;

    public SItemMeta(ItemStruct struct) {
        this.display = struct.getDisplay();
        this.priority = struct.getPriority();
        this.lore = struct.getLore();
        this.command = struct.getCommand();
    }

    public Integer getPriority() {
        if (priority == null) return 1000;
        return priority;
    }

    public String getDisplay() {
        if (display == null || display.equals("") || display.equals(" ")) return null;
        return Color.format(display);
    }

    public List<String> getLore() {
        if (lore == null || lore.isEmpty()) return null;
        return lore.stream().map(Color::format).collect(Collectors.toList());
    }
}
