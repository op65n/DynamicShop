package tech.op65n.dynamicshop.engine.ui.interfaces;

import org.apache.commons.lang3.tuple.Pair;
import tech.op65n.dynamicshop.engine.components.EItemType;

import java.util.List;

public interface BukkitItemStack {

    int amount();

    Pair<EItemType, String> material();

    String display();

    List<String> lore();

}
