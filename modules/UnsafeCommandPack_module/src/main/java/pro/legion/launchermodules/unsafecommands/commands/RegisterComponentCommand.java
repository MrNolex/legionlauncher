package pro.legion.launchermodules.unsafecommands.commands;

import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;
import pro.legion.launchserver.components.Component;

public class RegisterComponentCommand extends Command {
    public RegisterComponentCommand(LaunchServer server) {
        super(server);
    }

    @Override
    public String getArgsDescription() {
        return "[name] [classname]";
    }

    @Override
    public String getUsageDescription() {
        return "register custom component";
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void invoke(String... args) throws Exception {
        verifyArgs(args, 2);
        Class clazz = Class.forName(args[1]);
        if (clazz == null) throw new ClassNotFoundException(args[1]);
        Component.providers.register(args[0], clazz);
    }
}
