package pro.legion.launchermodules.mirrorhelper.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pro.legion.launcher.Launcher;
import pro.legion.launchermodules.mirrorhelper.Config;
import pro.legion.launchermodules.mirrorhelper.CurseforgeAPI;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;
import pro.legion.utils.command.SubCommand;

public class CurseforgeCommand extends Command {
    private transient final Logger logger = LogManager.getLogger();
    private final Config config;
    private final CurseforgeAPI api;
    public CurseforgeCommand(LaunchServer server, Config config) {
        super(server);
        this.config = config;
        this.api = new CurseforgeAPI(config.curseforgeApiKey);
        this.childCommands.put("getMod", new SubCommand("[modId]", "Get mod info by id") {
            @Override
            public void invoke(String... args) throws Exception {
                verifyArgs(args, 1);
                var e = api.fetchModById(Long.parseLong(args[0]));
                logger.info("Response: {}", Launcher.gsonManager.configGson.toJson(e));
            }
        });
        this.childCommands.put("getModFile", new SubCommand("[modId] [fileId]", "Get mod file info by id") {
            @Override
            public void invoke(String... args) throws Exception {
                verifyArgs(args, 2);
                var e = api.fetchModFileById(Long.parseLong(args[0]), Long.parseLong(args[1]));
                logger.info("Response: {}", Launcher.gsonManager.configGson.toJson(e));
            }
        });
    }

    @Override
    public String getArgsDescription() {
        return "[action] [args]";
    }

    @Override
    public String getUsageDescription() {
        return "access curseforge api";
    }

    @Override
    public void invoke(String... args) throws Exception {
        invokeSubcommands(args);
    }
}
