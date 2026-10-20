package dev.bloko.platforminfo.fabric;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;

class FabricAdapterTest {
    @TempDir Path directory;
    @Test void savesAReportWithoutMinecraftClassesOrFabricApi() throws Exception {
        FabricLoader loader = mock(FabricLoader.class);
        when(loader.getConfigDir()).thenReturn(directory);
        ModContainer game = mock(ModContainer.class);
        ModMetadata metadata = mock(ModMetadata.class);
        Version version = mock(Version.class);
        when(version.getFriendlyString()).thenReturn("26.2-test");
        when(metadata.getVersion()).thenReturn(version);
        when(game.getMetadata()).thenReturn(metadata);
        when(loader.getModContainer("minecraft")).thenReturn(Optional.of(game));
        when(loader.getModContainer("fabricloader")).thenReturn(Optional.empty());
        try (MockedStatic<FabricLoader> access = mockStatic(FabricLoader.class)) {
            access.when(FabricLoader::getInstance).thenReturn(loader);
            new PlatformInfoFabric().onInitialize();
        }
        String report = Files.readString(directory.resolve("platforminfo/startup-report.txt"));
        assertTrue(report.contains("Version: 26.2-test"));
        assertFalse(report.contains("Online players:"));
        assertFalse(report.contains(directory.toString()));
    }
}
