package pro.legion.launchermodules.systemdnotify;

import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launchserver.modules.events.LaunchServerFullInitEvent;
import pro.legion.launchserver.modules.impl.LaunchServerInitContext;
import pro.legion.utils.Version;
import pro.legion.utils.helper.LogHelper;

import java.io.IOException;

public class SystemdNotifyModule extends LauncherModule {
    public static final Version version = new Version(1, 0, 0, 1, Version.Type.LTS);

    public SystemdNotifyModule() {
        super(new LauncherModuleInfo("SystemdNotifer", version, new String[]{"LaunchServerCore"}));
    }


    public void finish(LaunchServerFullInitEvent event) {
        notifySystemd();
    }

    public void notifySystemd() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("systemd-notify", "--ready");
        try {
            processBuilder.start();
            LogHelper.debug("Systemd notify successful");
        } catch (IOException e) {
            LogHelper.error(e);
        }
    }

    @Override
    public void init(LauncherInitContext initContext) {
        registerEvent(this::finish, LaunchServerFullInitEvent.class);
        if (initContext instanceof LaunchServerInitContext) {
            notifySystemd();
        }
    }
}
