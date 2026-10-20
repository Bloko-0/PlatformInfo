package dev.bloko.platforminfo.bukkit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

class BukkitAdapterTest {
    ServerMock server;
    @BeforeEach void start() { server = MockBukkit.mock(); MockBukkit.load(PlatformInfoBukkit.class); }
    @AfterEach void stop() { MockBukkit.unmock(); }
    @Test void registersCommandAndReportsToOperator() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        assertTrue(server.dispatchCommand(player, "platforminfo"));
        assertEquals("PlatformInfo 1.0.0", player.nextMessage());
        assertTrue(player.nextMessage().startsWith("Platform: "));
        assertTrue(player.nextMessage().startsWith("Version: "));
        assertEquals("Online players: 1", player.nextMessage());
        assertTrue(player.nextMessage().startsWith("Plugin uptime: "));
    }
    @Test void refusesUnprivilegedPlayer() {
        PlayerMock player = server.addPlayer();
        player.setOp(false);
        server.dispatchCommand(player, "pinfo");
        String message = player.nextMessage();
        assertNotNull(message);
        assertFalse(message.contains("PlatformInfo 1.0.0"));
    }
    @Test void supportsConsoleAndAlias() {
        assertTrue(server.dispatchCommand(server.getConsoleSender(), "pinfo"));
        assertEquals("PlatformInfo 1.0.0", server.getConsoleSender().nextMessage());
    }
}
