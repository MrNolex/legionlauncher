package pro.legion.launchermodules.discordgame;

import pro.legion.launcher.client.events.ClientEngineInitPhase;
import pro.legion.launcher.client.events.ClientExitPhase;
import pro.legion.launcher.client.events.ClientUnlockConsoleEvent;
import pro.legion.launcher.client.events.client.ClientProcessBuilderParamsWrittedEvent;
import pro.legion.launcher.client.events.client.ClientProcessLaunchEvent;
import pro.legion.launcher.modules.LauncherInitContext;
import pro.legion.launcher.modules.LauncherModule;
import pro.legion.launcher.modules.LauncherModuleInfo;
import pro.legion.launcher.request.Request;
import pro.legion.launchermodules.discordgame.commands.DiscordCommand;
import pro.legion.utils.Version;
import pro.legion.utils.helper.LogHelper;

public class ClientModule extends LauncherModule {
    public static final Version version = new Version(1, 1, 0, 1, Version.Type.LTS);
    private static final Object lock = new Object();
    public static Config config;
    private static volatile boolean isClosed = false;

    public ClientModule() {
        super(new LauncherModuleInfo("DiscordGame", version, new String[]{"ClientLauncherCore"}));
    }

    /**
     * @param flag Set closed to true?
     * @return Current closed.
     */
    public static boolean isClosed(boolean flag) {
        boolean ret;
        synchronized (lock) {
            ret = isClosed;
            if (flag) isClosed = true;
            lock.notify();
        }
        return ret;
    }


    @Override
    public void init(LauncherInitContext initContext) {
        config = new Config();
        registerEvent(this::clientInit, ClientProcessLaunchEvent.class);
        registerEvent(this::launcherInit, ClientEngineInitPhase.class);
        registerEvent(this::exitHandler, ClientExitPhase.class);
        registerEvent(this::exitByStartClient, ClientProcessBuilderParamsWrittedEvent.class);
    }

    private void clientInit(ClientProcessLaunchEvent phase) {
        DiscordBridge.activityService.onClientStart(phase.params);
        try {
            DiscordBridge.init(config.appId);
            RequestEventWatcher.INSTANCE = new RequestEventWatcher(true);
            Request.getRequestService().registerEventHandler(RequestEventWatcher.INSTANCE);
        } catch (Throwable e) {
            LogHelper.error(e);
        }
    }

    private void unlock(ClientUnlockConsoleEvent event) {
        event.handler.registerCommand("discord", new DiscordCommand());
    }

    private void launcherInit(ClientEngineInitPhase phase) {
        DiscordBridge.activityService.onLauncherStart();
        try {
            DiscordBridge.init(config.appId);
            RequestEventWatcher.INSTANCE = new RequestEventWatcher(false);
            Request.getRequestService().registerEventHandler(RequestEventWatcher.INSTANCE);
        } catch (Throwable e) {
            LogHelper.error(e);
        }
    }

    private void exitHandler(ClientExitPhase phase) {
        if (isClosed(true)) return;
        if (RequestEventWatcher.INSTANCE != null) Request.getRequestService().unregisterEventHandler(RequestEventWatcher.INSTANCE);
        DiscordBridge.close();
    }

    private void exitByStartClient(ClientProcessBuilderParamsWrittedEvent event) {

    }

}
