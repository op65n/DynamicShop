package com.sebbaindustries.dynamicshop.log;

public class EngineLogger implements ILog {

    public EngineLogger(String prefix) {
        this.prefix = prefix + " ";
    }

    private final String prefix;
    private boolean enabled = true;

    @Override
    public void log(String message) {
        if (!enabled) return;
        ILog.super.log(message);
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
