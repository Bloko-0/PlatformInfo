package dev.bloko.platforminfo.bukkit;

import dev.bloko.platforminfo.core.PlatformReport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PlatformInfoBukkit extends JavaPlugin {
    private long startedAt;

    @Override
    public void onEnable() {
        startedAt = System.nanoTime();
        getLogger().info("PlatformInfo enabled on " + getServer().getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("platforminfo.use")) {
            sender.sendMessage("You need platforminfo.use to view server information.");
            return true;
        }

        if (args.length > 0) {
            return false;
        }

        long uptime = (System.nanoTime() - startedAt) / 1_000_000_000L;

        for (String line : PlatformReport.create(
                getServer().getName(),
                getServer().getVersion(),
                getServer().getOnlinePlayers().size(),
                uptime)) {
            sender.sendMessage(line);
        }

        return true;
    }
}
