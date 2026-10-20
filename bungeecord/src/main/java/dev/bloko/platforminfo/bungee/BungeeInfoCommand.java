package dev.bloko.platforminfo.bungee;

import dev.bloko.platforminfo.core.PlatformReport;
import java.util.function.LongSupplier;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public final class BungeeInfoCommand extends Command {
    private final ProxyServer proxy;
    private final LongSupplier uptime;
    public BungeeInfoCommand(ProxyServer proxy, LongSupplier uptime) {
        super("platforminfo", "platforminfo.use", "pinfo");
        this.proxy = proxy;
        this.uptime = uptime;
    }
    @Override public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("platforminfo.use")) {
            sender.sendMessage(new TextComponent("You need platforminfo.use to view proxy information."));
            return;
        }
        if (args.length != 0) {
            sender.sendMessage(new TextComponent("Usage: /platforminfo"));
            return;
        }
        for (String line : PlatformReport.create(proxy.getName(), proxy.getVersion(),
                proxy.getOnlineCount(), uptime.getAsLong())) {
            sender.sendMessage(new TextComponent(line));
        }
    }
}
