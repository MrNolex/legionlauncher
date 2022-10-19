package pro.legion.launchermodules.unsafecommands;

import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launchermodules.unsafecommands.commands.*;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.modules.events.LaunchServerInitPhase;
import pro.legion.launchserver.modules.impl.LaunchServerInitContext;
import pro.legion.utils.Version;
import pro.legion.utils.command.BaseCommandCategory;
import pro.legion.utils.command.CommandHandler;

public class UnsafeCommandsModule extends LauncherModule {
    public static final Version version = new Version(1, 0, 0, 1, Version.Type.EXPERIMENTAL);

    public UnsafeCommandsModule() {
        super(new LauncherModuleInfo("UnsafeCommands", version, new String[]{"LaunchServerCore"}));
    }

    public void init(LaunchServer server) {
        BaseCommandCategory category = new BaseCommandCategory();
        category.registerCommand("loadJar", new LoadJarCommand(server));
        category.registerCommand("registerComponent", new RegisterComponentCommand(server));
        category.registerCommand("newDownloadAsset", new NewDownloadAssetCommand(server));
        category.registerCommand("newDownloadClient", new FetchClientCommand(server));
        category.registerCommand("sendAuth", new SendAuthCommand(server));
        category.registerCommand("patcher", new PatcherCommand(server));
        category.registerCommand("cipherList", new CipherListCommand(server));
        category.registerCommand("launchinstaller", new LaunchInstallerCommand(server));
        category.registerCommand("deduplibraries", new DeDupLibrariesCommand(server));
        category.registerCommand("patchauthlib", new PatchAuthlibCommand(server));
        category.registerCommand("lwjgldownload", new LwjglDownloadCommand(server));
        server.commandHandler.registerCategory(new CommandHandler.Category(category, "Unsafe"));
    }

    public void initPhase(LaunchServerInitPhase initPhase) {
        init(initPhase.server);
    }

    @Override
    public void init(LauncherInitContext initContext) {
        registerEvent(this::initPhase, LaunchServerInitPhase.class);
        if (initContext != null) {
            if (initContext instanceof LaunchServerInitContext) {
                init(((LaunchServerInitContext) initContext).server);
            }
        }
    }
}
