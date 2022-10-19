package pro.legion.launchermodules.remotecontrol.commands;

import pro.legion.launchermodules.remotecontrol.RemoteControlConfig;
import pro.legion.launchermodules.remotecontrol.RemoteControlModule;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;
import pro.legion.utils.helper.LogHelper;

public class ListCommand extends Command {
    public ListCommand(LaunchServer server) {
        super(server);
    }

    @Override
    public String getArgsDescription() {
        return "[]";
    }

    @Override
    public String getUsageDescription() {
        return "";
    }

    @Override
    public void invoke(String... args) throws Exception {
        RemoteControlModule module = server.modulesManager.getModule(RemoteControlModule.class);
        for (RemoteControlConfig.RemoteControlToken token : module.config.list) {
            LogHelper.info("Token %s... allow %s commands %s", token.token.substring(0, 5), token.allowAll ? "all" : String.valueOf(token.commands.size()), token.commands.isEmpty() ? "" : String.join(", ", token.commands));
        }
        LogHelper.info("Found %d tokens", module.config.list.size());
    }
}
