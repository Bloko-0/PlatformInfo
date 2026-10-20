package dev.bloko.platforminfo.bukkit;

import dev.bloko.platforminfo.core.PlatformReport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/** Uses the legacy Bukkit command API without world or entity mutations. */
public class PlatformInfoBukkit extends JavaPlugin {
    private long started;

    @Override public void onEnable() {
        started = System.nanoTime();
        getLogger().info("PlatformInfo enabled on " + getServer().getName());
    }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("platforminfo.use")) {
            sender.sendMessage("You need platforminfo.use to view server information.");
            return true;
        }
        if (args.length != 0) return false;
        for (String line : PlatformReport.create(getServer().getName(),
                getServer().getVersion(), getServer().getOnlinePlayers().size(),
                (System.nanoTime() - started) / 1_000_000_000L)) {
            sender.sendMessage(line);
        }
        return true;
    }
}
