package com.sebbaindustries.dynamicshop.engine;

public interface Engine {

    String codename();

    String version();

    long uptime();

    void initialize();

    void terminate();

    void reload();

    DynEngine instance();
}
