package com.sebbaindustries.dynamicshop.engine.structure;

public interface Serializable<T> {

    void serialize(String fileName);
    T deserialize();

}
