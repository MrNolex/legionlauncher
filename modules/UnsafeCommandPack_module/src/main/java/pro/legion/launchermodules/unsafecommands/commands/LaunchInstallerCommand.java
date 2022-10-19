package pro.legion.launchermodules.unsafecommands.commands;

import pro.legion.launchermodules.unsafecommands.commands.installers.FabricInstallerCommand;
import pro.legion.launchermodules.unsafecommands.commands.installers.ForgeInstallerCommand;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;

public class LaunchInstallerCommand extends Command {
    public LaunchInstallerCommand(LaunchServer server) {
        super(server);
        childCommands.put("fabric", new FabricInstallerCommand(server));
        childCommands.put("forge", new ForgeInstallerCommand(server));
    }

    @Override
    public String getArgsDescription() {
        return "[installer] [args]";
    }

    @Override
    public String getUsageDescription() {
        return "launch installer";
    }

    @Override
    public void invoke(String... args) throws Exception {
        invokeSubcommands(args);
    }
}
