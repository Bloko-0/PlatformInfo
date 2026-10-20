package dev.bloko.platforminfo.velocity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.ProxyVersion;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

class VelocityAdapterTest {
    @Test void reportsOnlyToPermittedSender() {
        ProxyServer proxy = mock(ProxyServer.class);
        CommandSource source = mock(CommandSource.class);
        SimpleCommand.Invocation invocation = mock(SimpleCommand.Invocation.class);
        when(invocation.source()).thenReturn(source);
        when(invocation.arguments()).thenReturn(new String[0]);
        VelocityInfoCommand command = new VelocityInfoCommand(proxy, () -> 75L);
        assertFalse(command.hasPermission(invocation));
        command.execute(invocation);
        verifyNoInteractions(proxy);
        clearInvocations(source);
        when(source.hasPermission("platforminfo.use")).thenReturn(true);
        ProxyVersion version = mock(ProxyVersion.class);
        when(version.getVersion()).thenReturn("3.4-test");
        when(proxy.getVersion()).thenReturn(version);
        when(proxy.getPlayerCount()).thenReturn(7);
        command.execute(invocation);
        verify(source).sendMessage(Component.text("Platform: Velocity"));
        verify(source).sendMessage(Component.text("Online players: 7"));
        verify(source).sendMessage(Component.text("Plugin uptime: 0d 00h 01m 15s"));
    }
    @Test void invalidArgumentsDoNotQueryTheProxy() {
        ProxyServer proxy = mock(ProxyServer.class);
        CommandSource source = mock(CommandSource.class);
        SimpleCommand.Invocation invocation = mock(SimpleCommand.Invocation.class);
        when(invocation.source()).thenReturn(source);
        when(invocation.arguments()).thenReturn(new String[]{"unexpected"});
        when(source.hasPermission("platforminfo.use")).thenReturn(true);
        new VelocityInfoCommand(proxy, () -> 0L).execute(invocation);
        verifyNoInteractions(proxy);
        verify(source).sendMessage(Component.text("Usage: /platforminfo"));
    }
}
