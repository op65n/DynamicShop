package com.sebbaindustries.dynamicshop.engine.structure;

public interface Serializable<T> {

    void serialize();

    T deserialize(String file);

}
