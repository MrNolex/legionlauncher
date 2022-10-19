package pro.legion.launchermodules.fileauthsystem;

import pro.legion.launchermodules.fileauthsystem.commands.InstallCommand;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;

public class FileAuthSystemCommand extends Command {
    private final FileAuthSystemModule module;

    public FileAuthSystemCommand(LaunchServer server, FileAuthSystemModule module) {
        super(server);
        this.module = module;
        this.childCommands.put("install", new InstallCommand(server, module));
    }

    @Override
    public String getArgsDescription() {
        return "[subcommand]";
    }

    @Override
    public String getUsageDescription() {
        return "manage FileAuthSystem";
    }

    @Override
    public void invoke(String... args) throws Exception {
        invokeSubcommands(args);
    }
}
