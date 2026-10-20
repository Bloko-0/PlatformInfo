package dev.bloko.platforminfo.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public final class PlatformInfoBungee extends Plugin {
    @Override public void onEnable() {
        final long started = System.nanoTime();
        getProxy().getPluginManager().registerCommand(this,
                new BungeeInfoCommand(getProxy(), () -> (System.nanoTime() - started) / 1_000_000_000L));
        getLogger().info("PlatformInfo enabled.");
    }
}
