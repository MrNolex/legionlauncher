package pro.legion.launcher.server.setup;

import pro.legion.utils.command.CommandHandler;
import pro.legion.utils.command.JLineCommandHandler;
import pro.legion.utils.command.StdCommandHandler;
import pro.legion.utils.helper.LogHelper;

import java.io.IOException;

public class ServerWrapperCommands {
    public final CommandHandler commandHandler;

    public ServerWrapperCommands(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public ServerWrapperCommands() throws IOException {
        // Set command handler
        CommandHandler localCommandHandler;
        try {
            Class.forName("org.jline.terminal.Terminal");

            // JLine2 available
            localCommandHandler = new JLineCommandHandler();
            LogHelper.info("JLine2 terminal enabled");
        } catch (ClassNotFoundException ignored) {
            localCommandHandler = new StdCommandHandler(true);
            LogHelper.warning("JLine2 isn't in classpath, using std");
        }
        commandHandler = localCommandHandler;
    }

    public void registerCommands() {
        //FUTURE
    }
}
