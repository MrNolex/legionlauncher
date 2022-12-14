package pro.legion.launcher.request.websockets;

import pro.legion.launcher.request.Request;
import pro.legion.launcher.request.RequestException;
import pro.legion.launcher.request.RequestService;
import pro.legion.launcher.request.WebSocketEvent;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class VoidRequestService implements RequestService {
    private final Throwable ex;

    public VoidRequestService(Throwable ex) {
        this.ex = ex;
    }

    public VoidRequestService() {
        this.ex = null;
    }

    @Override
    public <T extends WebSocketEvent> CompletableFuture<T> request(Request<T> request) throws IOException {
        CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(ex != null ? ex : new RequestException("Connection fail"));
        return future;
    }

    @Override
    public void registerEventHandler(EventHandler handler) {

    }

    @Override
    public void unregisterEventHandler(EventHandler handler) {

    }

    @Override
    public boolean isClosed() {
        return true;
    }
}
