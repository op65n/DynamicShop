package com.sebbaindustries.dynamicshop.engine.structure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class IconStruct {

    private String material = "ACACIA_BOAT";

    private String texture;

    private String base64;

    private String display;

    private List<String> lore;

}
