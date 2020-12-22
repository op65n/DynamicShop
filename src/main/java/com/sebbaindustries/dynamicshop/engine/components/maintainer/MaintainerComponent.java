package com.sebbaindustries.dynamicshop.engine.components.maintainer;

public class MaintainerComponent {

    private String reason;
    private boolean status;
    private String moduleName;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSetupSuccessful() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
