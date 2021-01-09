package com.sebbaindustries.dynamicshop.engine.components;

public enum EItemType {

    MATERIAL(0),
    TEXTURE(1),
    BASE64(2),
    ;

    public int type;

    EItemType(int type) {
        this.type = type;
    }
}
