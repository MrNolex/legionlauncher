package pro.legion.launchermodules.unsafecommands.commands;

import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.StarterAgent;
import pro.legion.launchserver.command.Command;
import pro.legion.utils.helper.LogHelper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarFile;

public class LoadJarCommand extends Command {
    public LoadJarCommand(LaunchServer server) {
        super(server);
    }

    @Override
    public String getArgsDescription() {
        return "[jarfile]";
    }

    @Override
    public String getUsageDescription() {
        return "Load jar file";
    }

    @Override
    public void invoke(String... args) throws Exception {
        verifyArgs(args, 1);
        Path file = Paths.get(args[0]);
        StarterAgent.inst.appendToSystemClassLoaderSearch(new JarFile(file.toFile()));
        LogHelper.info("File %s added to system classpath", file.toAbsolutePath().toString());
    }
}
