package dev.bloko.platforminfo.velocity;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.bloko.platforminfo.core.PlatformReport;
import java.util.function.LongSupplier;
import net.kyori.adventure.text.Component;

public final class VelocityInfoCommand implements SimpleCommand {
    private final ProxyServer proxy;
    private final LongSupplier uptime;

    public VelocityInfoCommand(ProxyServer proxy, LongSupplier uptime) {
        this.proxy = proxy;
        this.uptime = uptime;
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("platforminfo.use");
    }

    @Override
    public void execute(Invocation invocation) {
        if (!hasPermission(invocation)) {
            invocation.source().sendMessage(Component.text(
                    "You need platforminfo.use to view proxy information."));
            return;
        }

        if (invocation.arguments().length > 0) {
            invocation.source().sendMessage(Component.text("Usage: /platforminfo"));
            return;
        }

        for (String line : PlatformReport.create(
                "Velocity",
                proxy.getVersion().getVersion(),
                proxy.getPlayerCount(),
                uptime.getAsLong())) {
            invocation.source().sendMessage(Component.text(line));
        }
    }
}
