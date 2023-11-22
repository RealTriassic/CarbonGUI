package com.triassic.carbongui.velocity.listener;

import com.google.inject.Inject;
import com.triassic.carbongui.common.enums.MessageType;
import com.triassic.carbongui.common.messaging.MessageUtils;
import com.triassic.carbongui.velocity.CarbonGUIVelocity;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class PostConnectListener {

    private final ProxyServer proxy;

    @Inject
    public PostConnectListener(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Subscribe
    public void onPlayerConnect(final ServerPostConnectEvent event) {
        final UUID playerId = event.getPlayer().getUniqueId();

        Set<UUID> onlinePlayers = proxy.getAllPlayers().stream()
                .map(Player::getUniqueId)
                .collect(Collectors.toSet());

        for (final RegisteredServer server : proxy.getAllServers()) {
            byte[] pluginMessage = MessageUtils.createPluginMessage(
                    MessageType.PLAYER_JOIN,
                    playerId,
                    onlinePlayers
            );

            server.sendPluginMessage(CarbonGUIVelocity.CHANNEL, pluginMessage);
        }
    }
}
