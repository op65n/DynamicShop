package tech.op65n.dynamicshop.utils;

import tech.op65n.dynamicshop.Core;
import tech.op65n.dynamicshop.engine.Engine;

public class EngineUtils {

    public static String getCodename() {
        return Core.gCore().getEngine().getClass().getAnnotation(Engine.Codename.class).value();
    }

    public static String getVersion() {
        return Core.gCore().getEngine().getClass().getAnnotation(Engine.Version.class).value();
    }

}
