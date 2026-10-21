package dev.bloko.platforminfo.bungee;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class BungeeAdapterTest {
    @Test
    void checksPermissionAndDisplaysReport() {
        ProxyServer proxy = mock(ProxyServer.class);
        CommandSender sender = mock(CommandSender.class);
        BungeeInfoCommand command = new BungeeInfoCommand(proxy, () -> 3600);

        assertEquals("platforminfo.use", command.getPermission());
        assertArrayEquals(new String[]{"pinfo"}, command.getAliases());

        command.execute(sender, new String[0]);
        verifyNoInteractions(proxy);

        clearInvocations(sender);
        when(sender.hasPermission("platforminfo.use")).thenReturn(true);
        when(proxy.getName()).thenReturn("BungeeCord");
        when(proxy.getVersion()).thenReturn("test");
        when(proxy.getOnlineCount()).thenReturn(4);

        command.execute(sender, new String[0]);

        ArgumentCaptor<BaseComponent> messages = ArgumentCaptor.forClass(BaseComponent.class);
        verify(sender, times(5)).sendMessage(messages.capture());

        List<String> lines = messages.getAllValues().stream()
                .map(component -> component.toPlainText())
                .toList();

        assertTrue(lines.contains("Platform: BungeeCord"));
        assertTrue(lines.contains("Online players: 4"));
    }
}
