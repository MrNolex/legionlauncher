package pro.legion.launchermodules.unsafecommands.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pro.legion.launcher.AsyncDownloader;
import pro.legion.launchermodules.unsafecommands.impl.AssetDownloader;
import pro.legion.launchserver.LaunchServer;
import pro.legion.launchserver.command.Command;
import pro.legion.utils.Downloader;
import pro.legion.utils.helper.IOHelper;
import pro.legion.utils.helper.LogHelper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class NewDownloadAssetCommand extends Command {
    private final Logger logger = LogManager.getLogger();

    public NewDownloadAssetCommand(LaunchServer server) {
        super(server);
    }

    @Override
    public String getArgsDescription() {
        return "[version] [dir]";
    }

    @Override
    public String getUsageDescription() {
        return "Download asset dir";
    }

    @Override
    public void invoke(String... args) throws Exception {
        verifyArgs(args, 2);
        String version = args[0];
        String dirName = IOHelper.verifyFileName(args[1]);
        Path assetDir = server.updatesDir.resolve(dirName);

        // Create asset dir
        if(Files.notExists(assetDir)) {
            LogHelper.subInfo("Creating asset dir: '%s'", dirName);
            Files.createDirectory(assetDir);
        }

        LogHelper.subInfo("Getting asset index, it may take some time");
        List<AsyncDownloader.SizedFile> applies = AssetDownloader.listAssets(assetDir, version);
        applies.removeIf((f) -> {
            return Files.exists(assetDir.resolve(f.filePath));
        });
        // Download required asset
        LogHelper.subInfo("Downloading asset, it may take some time");
        long total = 0;
        for (AsyncDownloader.SizedFile file : applies) {
            total += file.size;
        }
        final long finalTotal = total;
        logger.info("Download started, total {}", finalTotal);
        Downloader downloader = downloadWithProgressBar(args[1], applies, AssetDownloader.getBase(), assetDir);
        downloader.getFuture().get();
        // Finished
        server.syncUpdatesDir(Collections.singleton(dirName));
    }
}