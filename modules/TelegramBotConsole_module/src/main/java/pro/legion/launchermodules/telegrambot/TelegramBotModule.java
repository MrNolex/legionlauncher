package pro.legion.launchermodules.telegrambot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pro.legion.launcher.config.JsonConfigurable;
import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.modules.events.LaunchServerFullInitEvent;
import pro.legion.launchserver.modules.impl.LaunchServerInitContext;
import pro.legion.utils.Version;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Path;

public class TelegramBotModule extends LauncherModule {
    public static final Version version = new Version(1, 0, 0, 1, Version.Type.STABLE);
    private static final Logger logger = LogManager.getLogger(TelegramBot.class);
    public TelegramBot.Config config;
    public JsonConfigurable<TelegramBot.Config> configurable;


    public TelegramBotModule() {
        super(new LauncherModuleInfo("TelegramBot", version, new String[]{"LaunchServerCore"}));
    }


    public void finish(LaunchServerFullInitEvent event) {
        init(event.server);
    }

    @Override
    public void init(LauncherInitContext initContext) {
        registerEvent(this::finish, LaunchServerFullInitEvent.class);
        if (initContext instanceof LaunchServerInitContext) {
            init(((LaunchServerInitContext) initContext).server);
        }
    }

    public void init(LaunchServer server) {
        Path path = modulesConfigManager.getModuleConfig(moduleInfo.name);
        TelegramBotModule module = this;
        configurable = new JsonConfigurable<>(TelegramBot.Config.class, path) {
            @Override
            public TelegramBot.Config getConfig() {
                return config;
            }

            @Override
            public void setConfig(TelegramBot.Config config) {
                module.config = config;
            }

            @Override
            public TelegramBot.Config getDefaultConfig() {
                return new TelegramBot.Config();
            }
        };
        try {
            configurable.loadConfig();
        } catch (IOException e) {
            logger.error(e);
            config = configurable.getDefaultConfig();
        }
        try {
            TelegramBot.initialize(config, server);
        } catch (LoginException e) {
            logger.error("TelegramBotModule disabled. Please set 'token'", e);
        }
        if (config.events.login) {
            server.authHookManager.postHook.registerHook((context, client) -> {
                TelegramBotListener.getInstance()
                        .sendNotify(String.format("""
                                        ???????????????????????? %s ?????????????????????????? ?? ????????????????.
                                        *UUID:* %s
                                        *AuthId:* %s""",
                                client.username, String.format("%s", client.uuid),
                                String.format("%s", client.auth.displayName)));
                return false;
            });
        }
        if (config.events.checkServer) {
            server.authHookManager.postCheckServerHook.registerHook((report, client) -> {
                String serverName = client.getProperty("launchserver.serverName");
                if (serverName == null) {
                    serverName = "Unknown";
                }
                TelegramBotListener.getInstance()
                        .sendNotify(String.format("???????????????????????? %s ???????????? ???? ???????????? %s\n" +
                                        "*UUID:* %s", report.playerProfile != null ?
                                        report.playerProfile.username : report.user.getUsername(), serverName,
                                String.format("%s", report.uuid)));
                return false;
            });
        }
    }
}
