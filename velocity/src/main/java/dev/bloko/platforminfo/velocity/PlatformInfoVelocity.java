package dev.bloko.platforminfo.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(id = "platforminfo", name = "PlatformInfo", version = "1.0.0",
        authors = {"Bloko"}, description = "Cross-platform server information utility")
public final class PlatformInfoVelocity {
    private final ProxyServer proxy;
    private final long started = System.nanoTime();

    @Inject public PlatformInfoVelocity(ProxyServer proxy) { this.proxy = proxy; }

    @Subscribe public void initialize(ProxyInitializeEvent event) {
        proxy.getCommandManager().register(
                proxy.getCommandManager().metaBuilder("platforminfo").aliases("pinfo").plugin(this).build(),
                new VelocityInfoCommand(proxy, () -> (System.nanoTime() - started) / 1_000_000_000L));
    }
}
