package pro.legion.launchermodules.discordgame.event;

import de.jcm.discordgamesdk.Core;
import pro.legion.launcher.modules.LauncherModule;

public class DiscordInitEvent extends LauncherModule.Event {
    public final Core core;

    public DiscordInitEvent(Core core) {
        this.core = core;
    }
}
