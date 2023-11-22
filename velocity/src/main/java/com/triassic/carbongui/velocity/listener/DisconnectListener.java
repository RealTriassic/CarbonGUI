package com.triassic.carbongui.velocity.listener;

import com.google.inject.Inject;
import com.triassic.carbongui.common.enums.MessageType;
import com.triassic.carbongui.common.messaging.MessageUtils;
import com.triassic.carbongui.velocity.CarbonGUIVelocity;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.UUID;

public final class DisconnectListener {

    private final ProxyServer proxy;

    @Inject
    public DisconnectListener(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Subscribe
    public void onPlayerDisconnect(final DisconnectEvent event) {
        final UUID playerId = event.getPlayer().getUniqueId();

        byte[] pluginMessage = MessageUtils.createPluginMessage(
                MessageType.PLAYER_QUIT,
                playerId,
                null
        );

        for (RegisteredServer server : proxy.getAllServers()) {
            server.sendPluginMessage(CarbonGUIVelocity.CHANNEL, pluginMessage);
        }
    }
}