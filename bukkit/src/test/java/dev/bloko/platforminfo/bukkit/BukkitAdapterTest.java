package dev.bloko.platforminfo.bukkit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

class BukkitAdapterTest {
    private ServerMock server;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        MockBukkit.load(PlatformInfoBukkit.class);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void reportsServerInfoToOperators() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);

        assertTrue(server.dispatchCommand(player, "platforminfo"));
        assertEquals("PlatformInfo 1.0.0", player.nextMessage());
        assertTrue(player.nextMessage().startsWith("Platform: "));
        assertTrue(player.nextMessage().startsWith("Version: "));
        assertEquals("Online players: 1", player.nextMessage());
        assertTrue(player.nextMessage().startsWith("Plugin uptime: "));
    }

    @Test
    void deniesPlayersWithoutPermission() {
        PlayerMock player = server.addPlayer();
        player.setOp(false);

        server.dispatchCommand(player, "pinfo");

        String message = player.nextMessage();
        assertNotNull(message);
        assertFalse(message.contains("PlatformInfo 1.0.0"));
    }

    @Test
    void supportsConsoleAndAlias() {
        assertTrue(server.dispatchCommand(server.getConsoleSender(), "pinfo"));
        assertEquals("PlatformInfo 1.0.0", server.getConsoleSender().nextMessage());
    }
}
