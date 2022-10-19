package pro.legion.launchermodules.discordgame;

import pro.legion.launcher.events.request.AuthRequestEvent;
import pro.legion.launcher.request.RequestService;
import pro.legion.launcher.request.WebSocketEvent;
import pro.legion.launcher.request.websockets.ClientWebSocketService;

public class RequestEventWatcher implements RequestService.EventHandler {
    public static RequestEventWatcher INSTANCE;
    public final boolean isClientInstance;

    public RequestEventWatcher(boolean isClientInstance) {
        this.isClientInstance = isClientInstance;
    }

    @Override
    public <T extends WebSocketEvent> boolean eventHandle(T event) {
        if (event instanceof AuthRequestEvent && ((AuthRequestEvent) event).playerProfile != null) {
            AuthRequestEvent event1 = (AuthRequestEvent) event;
            DiscordBridge.activityService.onLauncherAuth(event1.playerProfile);
        }
        return false;
    }
}
