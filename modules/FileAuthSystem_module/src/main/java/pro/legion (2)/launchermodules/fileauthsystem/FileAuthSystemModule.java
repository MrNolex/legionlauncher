package pro.legion.launchermodules.fileauthsystem;

import pro.legion.launcher.config.JsonConfigurable;
import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launcher.modules.events.PreConfigPhase;
import pro.legion.launchermodules.fileauthsystem.providers.FileSystemAuthCoreProvider;
import pro.legion.launchserver.auth.core.AuthCoreProvider;
import pro.legion.launchserver.auth.core.User;
import pro.legion.launchserver.auth.core.UserSession;
import pro.legion.launchserver.modules.events.LaunchServerFullInitEvent;
import pro.legion.utils.Version;
import pro.legion.utils.helper.SecurityHelper;

import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

public class FileAuthSystemModule extends LauncherModule {
    public static final Version version = new Version(1, 0, 0, 1, Version.Type.LTS);
    public JsonConfigurable<FileAuthSystemConfig> jsonConfigurable;
    private Path dbPath;

    public FileAuthSystemModule() {
        super(new LauncherModuleInfo("FileAuthSystem", version, new String[]{"LaunchServerCore"}));
    }

    public void preConfig(PreConfigPhase preConfigPhase) {
        AuthCoreProvider.providers.register("fileauthsystem", FileSystemAuthCoreProvider.class);
    }

    public void finish(LaunchServerFullInitEvent event) {
        event.server.commandHandler.registerCommand("fileauthsystem", new FileAuthSystemCommand(event.server, this));
    }

    @Override
    public void init(LauncherInitContext initContext) {
        registerEvent(this::preConfig, PreConfigPhase.class);
        registerEvent(this::finish, LaunchServerFullInitEvent.class);
        dbPath = modulesConfigManager.getModuleConfigDir(moduleInfo.name);
        jsonConfigurable = modulesConfigManager.getConfigurable(FileAuthSystemConfig.class, moduleInfo.name);
    }

    public Path getDatabasePath() {
        return modulesConfigManager.getModuleConfigDir(moduleInfo.name);
    }

}
