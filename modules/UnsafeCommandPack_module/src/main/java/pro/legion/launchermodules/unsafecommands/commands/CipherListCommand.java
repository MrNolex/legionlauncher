package pro.legion.launchermodules.unsafecommands.commands;

import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;
import pro.legion.utils.helper.LogHelper;

import java.security.Provider;
import java.security.Security;

public class CipherListCommand extends Command {
    public CipherListCommand(LaunchServer server) {
        super(server);
    }

    @Override
    public String getArgsDescription() {
        return "[]";
    }

    @Override
    public String getUsageDescription() {
        return "list all available ciphers";
    }

    @Override
    public void invoke(String... args) throws Exception {
        for (Provider provider : Security.getProviders()) {
            LogHelper.info("Provider %s | %s", provider.getName(), provider.getClass().getName());
            for (Provider.Service service : provider.getServices()) {
                LogHelper.subInfo("Service %s | alg %s", service.getClassName(), service.getAlgorithm());
            }
        }
    }
}
