package pro.legion.launchserver.auth.texture;

import pro.legion.launcher.profiles.Texture;

import java.util.UUID;

public final class VoidTextureProvider extends TextureProvider {

    @Override
    public void close() {
        // Do nothing
    }

    @Override
    public Texture getCloakTexture(UUID uuid, String username, String client) {
        return null; // Always nothing
    }

    @Override
    public Texture getSkinTexture(UUID uuid, String username, String client) {
        return null; // Always nothing
    }
}
