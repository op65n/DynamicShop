package com.sebbaindustries.dynamicshop.log;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public class DevLogger implements ILog {

    private final String prefix;
    private boolean enabled = true;
    public DevLogger(String prefix) {
        this.prefix = prefix + " ";
    }

    @Override
    public void log(String message) {
        if (!enabled) return;
        ILog.super.log(prefix + message);
    }

    @Override
    public void logWarn(String message) {
        if (!enabled) return;
        ILog.super.logWarn(prefix + message);
    }

    @Override
    public void logSevere(String message) {
        if (!enabled) return;
        ILog.super.logSevere(prefix + message);
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
