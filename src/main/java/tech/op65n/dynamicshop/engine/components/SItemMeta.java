package tech.op65n.dynamicshop.engine.components;

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

    public Integer getPriority() {
        if (priority == null) return 1000;
        return priority;
    }

    public String getDisplay() {
        return Color.format(display);
    }

    public List<String> getLore() {
        return lore.stream().map(Color::format).collect(Collectors.toList());
    }
}
