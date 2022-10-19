package pro.legion.launchermodules.remotecontrol.commands;

import pro.legion.launchermodules.remotecontrol.RemoteControlModule;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;
import pro.legion.utils.helper.LogHelper;

public class EnabledCommand extends Command {
    public EnabledCommand(LaunchServer server) {
        super(server);
    }

    @Override
    public String getArgsDescription() {
        return "[true/false]";
    }

    @Override
    public String getUsageDescription() {
        return "enable or disable RemoteControl module";
    }

    @Override
    public void invoke(String... args) throws Exception {
        verifyArgs(args, 1);
        RemoteControlModule module = server.modulesManager.getModule(RemoteControlModule.class);
        module.config.enabled = Boolean.parseBoolean(args[0]);
        module.configurable.saveConfig();
        LogHelper.info("Param config.enabled updated");
    }
}
