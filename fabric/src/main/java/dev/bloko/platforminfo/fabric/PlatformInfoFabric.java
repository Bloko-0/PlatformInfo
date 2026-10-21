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

public final class PlatformInfoFabric implements ModInitializer {
    private static final Logger LOGGER = Logger.getLogger("PlatformInfo");

    @Override
    public void onInitialize() {
        FabricLoader loader = FabricLoader.getInstance();

        String minecraftVersion = getVersion(loader, "minecraft");
        String fabricVersion = getVersion(loader, "fabricloader");

        // Only the version information is available from the loader at startup.
        List<String> report = PlatformReport.create(
                "Fabric Loader " + fabricVersion, minecraftVersion, 0, 0
        ).subList(0, 3);

        Path folder = loader.getConfigDir().resolve("platforminfo");

        try {
            Files.createDirectories(folder);
            Files.write(folder.resolve("startup-report.txt"), report, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.warning("Could not save startup-report.txt");
        }

        for (String line : report) {
            LOGGER.info(line);
        }
    }

    private String getVersion(FabricLoader loader, String modId) {
        return loader.getModContainer(modId)
                .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
                .orElse("Unknown");
    }
}
