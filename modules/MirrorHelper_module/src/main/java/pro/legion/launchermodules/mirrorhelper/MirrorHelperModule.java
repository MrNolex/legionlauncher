package pro.legion.launchermodules.mirrorhelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pro.legion.launcher.config.JsonConfigurable;
import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launchermodules.mirrorhelper.commands.CurseforgeCommand;
import pro.legion.launchermodules.mirrorhelper.commands.InstallClientCommand;
import pro.legion.launchermodules.mirrorhelper.commands.InstallModCommand;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.modules.events.LaunchServerFullInitEvent;
import pro.legion.launchserver.modules.impl.LaunchServerInitContext;
import pro.legion.utils.Version;
import pro.legion.utils.command.BaseCommandCategory;
import pro.legion.utils.command.CommandCategory;
import pro.legion.utils.command.CommandHandler;
import pro.legion.utils.helper.LogHelper;

import java.io.IOException;
import java.nio.file.Path;

public class MirrorHelperModule extends LauncherModule {
    public static final Version version = new Version(1, 0, 0, 1, Version.Type.LTS);
    private final Logger logger = LogManager.getLogger();
    public Config config;
    public JsonConfigurable<Config> configurable;

    public MirrorHelperModule() {
        super(new LauncherModuleInfo("MirrorHelper", version, new String[]{"LaunchServerCore", "UnsafeCommands"}));
    }

    public Path getWorkspaceDir() {
        return modulesConfigManager.getModuleConfigDir(moduleInfo.name).resolve("workspace");
    }


    public void finish(LaunchServerFullInitEvent event) {
        initialize(event.server);
    }

    public void initialize(LaunchServer server) {
        MirrorHelperModule module = this;
        configurable = new JsonConfigurable<>(Config.class, modulesConfigManager.getModuleConfig(moduleInfo.name)) {
            @Override
            public Config getConfig() {
                return config;
            }

            @Override
            public void setConfig(Config config) {
                module.config = config;
            }

            @Override
            public Config getDefaultConfig() {
                return new Config();
            }
        };
        try {
            configurable.loadConfig();
        } catch (IOException e) {
            logger.error(e);
            config = configurable.getDefaultConfig();
        }
        CommandCategory commands = new BaseCommandCategory();
        commands.registerCommand("curseforge", new CurseforgeCommand(server, config));
        commands.registerCommand("installClient", new InstallClientCommand(server, this));
        commands.registerCommand("installMods", new InstallModCommand(server, this));
        CommandHandler.Category category = new CommandHandler.Category(commands, "mirror");
        server.commandHandler.registerCategory(category);
    }

    @Override
    public void init(LauncherInitContext initContext) {
        registerEvent(this::finish, LaunchServerFullInitEvent.class);
        if (initContext instanceof LaunchServerInitContext) {
            initialize(((LaunchServerInitContext) initContext).server);
        }
    }
}
