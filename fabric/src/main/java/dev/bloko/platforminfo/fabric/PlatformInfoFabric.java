package dev.bloko.platforminfo.fabric;

import dev.bloko.platforminfo.core.PlatformReport;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

/** Loader-only server utility: no mapped Minecraft classes or Fabric API dependency. */
public final class PlatformInfoFabric implements ModInitializer {
    @Override public void onInitialize() {
        FabricLoader loader = FabricLoader.getInstance();
        String minecraft = loader.getModContainer("minecraft")
                .map(mod -> mod.getMetadata().getVersion().getFriendlyString()).orElse("Unknown");
        String loaderVersion = loader.getModContainer("fabricloader")
                .map(mod -> mod.getMetadata().getVersion().getFriendlyString()).orElse("Unknown");
        List<String> report = PlatformReport.create("Fabric Loader " + loaderVersion, minecraft, 0, 0).subList(0, 3);
        Path reportFile = loader.getConfigDir().resolve("platforminfo").resolve("startup-report.txt");
        try {
            Files.createDirectories(reportFile.getParent());
            Files.write(reportFile, report, StandardCharsets.UTF_8);
        } catch (IOException error) {
            // Do not log user-folder paths or operating-system exception messages.
            Logger.getLogger("PlatformInfo").warning("Could not save startup-report.txt; see the startup report below.");
        }
        for (String line : report) Logger.getLogger("PlatformInfo").info(line);
    }
}
