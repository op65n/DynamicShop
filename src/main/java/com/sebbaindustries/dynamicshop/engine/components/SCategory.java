package com.sebbaindustries.dynamicshop.engine.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@NoArgsConstructor
public class SCategory {

    private int ID;

    private int priority;

    private SIcon icon = new SIcon();

    private Map<Integer, SItem> items = new TreeMap<>();

}
