package com.triassic.carbongui.velocity;

import com.google.inject.Inject;
import com.triassic.carbongui.common.CarbonGUI;
import com.triassic.carbongui.common.Constants;
import com.triassic.carbongui.common.Platform;
import com.triassic.carbongui.velocity.listener.DisconnectListener;
import com.triassic.carbongui.velocity.listener.PostConnectListener;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Plugin(
        id = "carbongui",
        name = "CarbonGUI",
        description = "An addon for CarbonChat to add various GUIs",
        version = Constants.VERSION,
        authors = {"Triassic"}
)

public final class CarbonGUIVelocity implements Platform {

    public static final ChannelIdentifier CHANNEL = MinecraftChannelIdentifier.create("carbongui", "main");

    @Inject
    private ProxyServer proxy;
    @Inject
    private Logger logger;
    @Inject
    @DataDirectory
    private Path dataFolder;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        new CarbonGUI(this, dataFolder);
        proxy.getChannelRegistrar().register(CHANNEL);
        proxy.getEventManager().register(this, new DisconnectListener(proxy));
        proxy.getEventManager().register(this, new PostConnectListener(proxy));
    }

    @Override
    public @Nullable String username(final @NotNull UUID playerId) {
        return null;
    }

    @Override
    public void updateScoreboard(
            final @NotNull UUID playerId,
            final @NotNull Component title,
            final @NotNull List<Component> lines
    ) {
        throw new UnsupportedOperationException("Scoreboard functionality is not supported on this platform");
    }

    @Override
    public void deleteScoreboard(final @NotNull UUID playerId) {
        throw new UnsupportedOperationException("Scoreboard functionality is not supported on this platform");
    }

    @Override
    public @NotNull Logger logger() {
        return this.logger;
    }

    @Override
    public boolean isProxy() {
        return true;
    }
}
