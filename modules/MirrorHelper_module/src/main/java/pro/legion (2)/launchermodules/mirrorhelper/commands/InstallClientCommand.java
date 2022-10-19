package pro.legion.launchermodules.mirrorhelper.commands;

import pro.legion.launcher.profiles.ClientProfile;
import pro.legion.launchermodules.mirrorhelper.InstallClient;
import pro.legion.launchermodules.mirrorhelper.MirrorHelperModule;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InstallClientCommand extends Command {
    private final MirrorHelperModule module;
    public InstallClientCommand(LaunchServer server, MirrorHelperModule module) {
        super(server);
        this.module = module;
    }

    @Override
    public String getArgsDescription() {
        return "[name] [version] [versionType] (mods)";
    }

    @Override
    public String getUsageDescription() {
        return "";
    }

    @Override
    public void invoke(String... args) throws Exception {
        verifyArgs(args, 3);
        String name = args[0];
        ClientProfile.Version version = ClientProfile.Version.byName(args[1]);
        InstallClient.VersionType versionType = InstallClient.VersionType.valueOf(args[2]);
        List<Long> mods = new ArrayList<>();
        if(args.length > 3) {
            mods = Arrays.stream(args[3].split(",")).map(Long::parseLong).toList();
        }
        InstallClient run = new InstallClient(server, module.config, module.getWorkspaceDir(), name, version, mods, versionType);
        run.run();
    }
}
