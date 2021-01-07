package com.sebbaindustries.dynamicshop.engine;

import com.sebbaindustries.dynamicshop.engine.cache.LocalCache;
import com.sebbaindustries.dynamicshop.engine.container.ShopContainer;
import com.sebbaindustries.dynamicshop.engine.ui.ShopUI;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Engine {

    long uptime();

    boolean initialize();

    void terminate();

    void reload();

    ShopContainer container();

    LocalCache _LCACHE();

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


