package pro.legion.launcher.console;

import pro.legion.launcher.LauncherEngine;
import pro.legion.utils.command.Command;
import pro.legion.utils.helper.LogHelper;

import java.util.Base64;

public class SignDataCommand extends Command {
    private final LauncherEngine engine;

    public SignDataCommand(LauncherEngine engine) {
        this.engine = engine;
    }

    @Override
    public String getArgsDescription() {
        return "[base64 data]";
    }

    @Override
    public String getUsageDescription() {
        return "sign any data";
    }

    @Override
    public void invoke(String... args) throws Exception {
        verifyArgs(args, 1);
        byte[] data = Base64.getDecoder().decode(args[0]);
        byte[] signature = engine.sign(data);
        String base64 = Base64.getEncoder().encodeToString(signature);
        LogHelper.info("Signature: %s", base64);
    }
}
