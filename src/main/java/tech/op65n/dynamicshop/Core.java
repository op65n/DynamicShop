package tech.op65n.dynamicshop;

import tech.op65n.dynamicshop.global.GlobalCore;
import tech.op65n.dynamicshop.global.PluginCoreLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author <b>SebbaIndustries</b>
 * @version <b>1.0</b>
 */
public final class Core extends JavaPlugin {

    public static GlobalCore globalCore = null;
    private final PluginCoreLoader plugin = new PluginCoreLoader(this);

    /**
     * Safe way to get GlobalCore class
     *
     * @return GlobalCore class instance
     */
    @NotNull
    public static GlobalCore gCore() {
        if (globalCore == null) {
            throw new IllegalAccessError("Plugin core dumped due to illegal access of GlobalCore class!");
        }
        return globalCore;
    }


    @Override
    public void onLoad() {
        super.onLoad();
        plugin.load();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        plugin.disable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        plugin.enable();
    }

}
