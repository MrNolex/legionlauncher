package pro.legion.launchermodules.remotecontrol.commands;

import pro.legion.launchermodules.remotecontrol.RemoteControlModule;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;
import pro.legion.utils.helper.LogHelper;

public class ReloadCommand extends Command {
    public ReloadCommand(LaunchServer server) {
        super(server);
    }

    @Override
    public String getArgsDescription() {
        return "[]";
    }

    @Override
    public String getUsageDescription() {
        return "Reload config";
    }

    @Override
    public void invoke(String... args) throws Exception {
        RemoteControlModule module = server.modulesManager.getModule(RemoteControlModule.class);
        module.configurable.loadConfig();
        LogHelper.info("RemoteControl config reloaded");
    }
}
