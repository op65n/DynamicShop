package tech.op65n.dynamicshop.engine.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SIcon {

    private EItemType type = EItemType.MATERIAL;

    private String item = "ACACIA_BOAT";

    private String display;

    private List<String> lore;

}
