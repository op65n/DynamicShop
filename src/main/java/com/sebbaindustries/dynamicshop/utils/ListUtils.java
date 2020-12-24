package com.sebbaindustries.dynamicshop.utils;

import com.rits.cloning.Cloner;

import java.util.List;

public class ListUtils<T> {

    public ListUtils(List<T> list) {
        this.list = new Cloner().deepClone(list);
    }

    private final List<T> list;

    public T getNext() {
        if (list.isEmpty()) return null;
        T entry = list.get(0);
        list.remove(0);
        return entry;
    }

}
