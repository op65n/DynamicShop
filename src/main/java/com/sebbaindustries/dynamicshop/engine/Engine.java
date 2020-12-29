package com.sebbaindustries.dynamicshop.engine;

import com.sebbaindustries.dynamicshop.engine.container.ShopContainer;
import com.sebbaindustries.dynamicshop.engine.ui.ShopUI;

import java.lang.annotation.*;

public interface Engine {

    long uptime();

    void initialize();

    void terminate();

    void reload();

    ShopContainer container();

    ShopUI ui();

    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface Codename {

        String value() default "N/A";

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface Version {

        String value() default "N/A";

    }
}


