package pro.legion.launchermodules.mojangsupport;

import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launcher.modules.events.PreConfigPhase;
import pro.legion.launchserver.auth.core.AuthCoreProvider;
import pro.legion.launchserver.modules.impl.LaunchServerInitContext;
import pro.legion.utils.Version;

public class ModuleImpl extends LauncherModule {
    public static final Version version = new Version(1, 0, 0, 1, Version.Type.LTS);
    private static boolean registred = false;

    public ModuleImpl() {
        super(new LauncherModuleInfo("LegacySupport", version, new String[]{"LaunchServerCore"}));
    }


    public void preInit(PreConfigPhase preConfigPhase) {
        if (!registred) {
            AuthCoreProvider.providers.register("mojang", MojangAuthCoreProvider.class);
            AuthCoreProvider.providers.register("microsoft", MicrosoftAuthCoreProvider.class);
            registred = true;
        }
    }

    @Override
    public void init(LauncherInitContext initContext) {
        registerEvent(this::preInit, PreConfigPhase.class);
        if (initContext != null) {
            if (initContext instanceof LaunchServerInitContext) {
                preInit(new PreConfigPhase());
            }
        }
    }
}
