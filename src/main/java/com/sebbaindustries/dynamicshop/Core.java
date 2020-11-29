package com.sebbaindustries.dynamicshop;

import com.sebbaindustries.dynamicshop.global.GlobalCore;
import com.sebbaindustries.dynamicshop.global.ServerPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * <b>MIT License</b><br>
 * <br>
 * <b>Copyright (c) 2020 SebbaIndustries</b><br>
 * <br>
 * Permission is hereby granted, free of charge, to any person obtaining a copy <br>
 * of this software and associated documentation files (the "Software"), to deal <br>
 * in the Software without restriction, including without limitation the rights <br>
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell <br>
 * copies of the Software, and to permit persons to whom the Software is <br>
 * furnished to do so, subject to the following conditions: <br>
 * <br>
 * The above copyright notice and this permission notice shall be included in all <br>
 * copies or substantial portions of the Software. <br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR <br>
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, <br>
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE <br>
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER <br>
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, <br>
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE <br>
 * SOFTWARE. <br>
 * <br>
 *
 * @author <b>SebbaIndustries</b>
 * @version <b>1.0</b>
 */
public final class Core extends JavaPlugin {

    public static GlobalCore globalCore = null;
    private final ServerPlugin plugin = ServerPlugin.INSTANCE();

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
        plugin.load(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        plugin.terminate();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        plugin.initialize();
    }

    @Override
    public @NotNull Logger getLogger() {
        return super.getLogger();
    }
}
