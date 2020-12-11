package com.sebbaindustries.dynamicshop.engine.components.maintainer;

import com.sebbaindustries.dynamicshop.log.PluginLogger;

import java.util.ArrayList;
import java.util.List;

public class ComponentManager {

    private final List<MaintainerComponent> components = new ArrayList<>();

    public void logAll() {
        logSuccessful();
        logFailed();
    }

    public void logFailed() {
        components.forEach(component -> {
            if (component.isSetupSuccessful()) return;
            PluginLogger.logWarn("Component " + component.getModuleName() + " failed on status with reason: " + component.getReason());
        });
    }

    public void logSuccessful() {
        components.forEach(component -> {
            if (!component.isSetupSuccessful()) return;
            PluginLogger.log("Component " + component.getModuleName() + " started successfully!");
        });
    }

    private static ComponentManager componentManager;

    public static ComponentManager getInstance() {
        if (componentManager == null) {
            componentManager = new ComponentManager();
        }
        return componentManager;
    }

    public static void addComponent(MaintainerComponent component) {
        getInstance().components.add(component);
    }

    public static <T> void addComponent(Class<T> cl) {
        MaintainerComponent component = new MaintainerComponent();
        component.setModuleName(cl.getName());
        component.setStatus(true);
        component.setReason("Success!");
        getInstance().components.add(component);
    }

    public static <T> void addComponent(Class<T> cl, String reason) {
        MaintainerComponent component = new MaintainerComponent();
        component.setModuleName(cl.getName());
        component.setStatus(false);
        component.setReason(reason);
        getInstance().components.add(component);
    }

}
