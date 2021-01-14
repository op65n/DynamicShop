package tech.op65n.dynamicshop.engine.cache;

import tech.op65n.dynamicshop.utils.FileUtils;
import tech.op65n.dynamicshop.utils.ObjectUtils;
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

    public void init() {
        if (!FileUtils.fileExists(".cache/startup_info.json")) {
            ObjectUtils.saveGsonFile(".cache/startup_info.json", startupInfo);
        }
        startupInfo = ObjectUtils.getGsonFile(".cache/startup_info.json", LocalCache.StartupInfo.class);
    }

}
