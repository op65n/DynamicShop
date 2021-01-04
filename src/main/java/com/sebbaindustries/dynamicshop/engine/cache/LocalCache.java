package com.sebbaindustries.dynamicshop.engine.cache;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocalCache {

    private StartupInfo startupInfo = new StartupInfo();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StartupInfo {
        private boolean initStartup = true;
        private boolean configured = false;
        private boolean dbReady = false;
    }

}
