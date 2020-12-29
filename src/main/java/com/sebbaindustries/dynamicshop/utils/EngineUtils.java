package com.sebbaindustries.dynamicshop.utils;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.engine.Engine;

public class EngineUtils {

    public static String getCodename() {
        return Core.gCore().getEngine().getClass().getAnnotation(Engine.Codename.class).value();
    }

    public static String getVersion() {
        return Core.gCore().getEngine().getClass().getAnnotation(Engine.Version.class).value();
    }

}
