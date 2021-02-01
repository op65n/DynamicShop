package tech.op65n.dynamicshop.utils;

import tech.op65n.dynamicshop.engine.components.EItemType;
import org.apache.commons.lang3.tuple.Pair;

public class ShopUtils {

    public static Pair<EItemType, String> getTypeAndItemPair(String material, String texture, String base64) {
        if (base64 != null) return Pair.of(EItemType.BASE64, base64);
        if (texture != null) return Pair.of(EItemType.TEXTURE, texture);
        if (material != null) return Pair.of(EItemType.MATERIAL, material.toUpperCase());
        return Pair.of(EItemType.MATERIAL, "STRUCTURE_VOID");
    }
}
