package pro.legion.launchermodules.addhash;

import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launcher.modules.events.PreConfigPhase;
import pro.legion.launchermodules.addhash.verifier.BCryptPasswordVerifier;
import pro.legion.launchermodules.addhash.verifier.PhpHashPasswordVerifier;
import pro.legion.launchserver.auth.password.PasswordVerifier;
import pro.legion.utils.Version;

public class AddHashModule extends LauncherModule {
    public static final Version version = new Version(1, 0, 2, 1, Version.Type.STABLE);
    private static boolean registred = false;

    public AddHashModule() {
        super(new LauncherModuleInfo("AddHash", version, new String[]{"LaunchServerCore"}));
    }


    public void preInit(PreConfigPhase preConfigPhase) {
        if (!registred) {
            PasswordVerifier.providers.register("bcrypt-alt", BCryptPasswordVerifier.class);
            PasswordVerifier.providers.register("phpass", PhpHashPasswordVerifier.class);
            registred = true;
        }
    }

    @Override
    public void init(LauncherInitContext initContext) {
        registerEvent(this::preInit, PreConfigPhase.class);
    }
}
