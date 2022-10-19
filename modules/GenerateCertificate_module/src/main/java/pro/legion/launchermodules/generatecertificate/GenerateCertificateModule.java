package pro.legion.launchermodules.generatecertificate;

import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.modules.events.LaunchServerFullInitEvent;
import pro.legion.launchserver.modules.impl.LaunchServerInitContext;
import pro.legion.utils.Version;

public class GenerateCertificateModule extends LauncherModule {
    public GenerateCertificateModule() {
        super(new LauncherModuleInfo("GenerateCertificate", new Version(1, 0, 0), new String[]{"LaunchServerCore"}));
    }

    @Override
    public void init(LauncherInitContext initContext) {
        if (initContext instanceof LaunchServerInitContext) {
            initGenerateCertificate(((LaunchServerInitContext) initContext).server);
        } else {
            registerEvent(this::registerCommand, LaunchServerFullInitEvent.class);
        }
    }

    public void registerCommand(LaunchServerFullInitEvent event) {
        initGenerateCertificate(event.server);
    }

    public void initGenerateCertificate(LaunchServer server) {
        server.commandHandler.registerCommand("generatecertificate", new GenerateCertificateCommand(server));
    }
}
