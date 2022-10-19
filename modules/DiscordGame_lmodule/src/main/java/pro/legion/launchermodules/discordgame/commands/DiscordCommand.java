package pro.legion.launchermodules.discordgame.commands;

import pro.legion.utils.command.Command;

public class DiscordCommand extends Command {
    @Override
    public String getArgsDescription() {
        return "[subcommand] [args...]";
    }

    @Override
    public String getUsageDescription() {
        return "discord connection manager";
    }

    @Override
    public void invoke(String... args) throws Exception {
        invokeSubcommands(args);
    }
}
