package com.triassic.carbongui.paper.listener.input;

import com.triassic.carbongui.common.enums.MessageType;
import com.triassic.carbongui.common.manager.PlayerManager;
import com.triassic.carbongui.common.manager.ScoreboardManager;
import com.triassic.carbongui.common.messaging.MessageUtils;
import com.triassic.carbongui.common.messaging.PluginMessage;
import com.triassic.carbongui.paper.CarbonGUIPaper;
import net.draycia.carbon.api.users.CarbonPlayer;
import net.draycia.carbon.api.users.Party;
import net.draycia.carbon.api.users.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public final class MessageListener implements PluginMessageListener {

    private final Logger logger;
    private final UserManager<?> userManager;
    private final PlayerManager playerManager;
    private final ScoreboardManager scoreboardManager;

    public MessageListener(
            final Logger logger,
            final UserManager<?> userManager,
            final PlayerManager playerManager,
            final ScoreboardManager scoreboardManager
    ) {
        this.logger = logger;
        this.userManager = userManager;
        this.playerManager = playerManager;
        this.scoreboardManager = scoreboardManager;
    }

    @Override
    public void onPluginMessageReceived(
            @NotNull String channel,
            @NotNull Player player,
            byte @NotNull [] bytes
    ) {
        if (!channel.equals(CarbonGUIPaper.CHANNEL))
            return;

        final PluginMessage pluginMessage = MessageUtils.readPluginMessage(bytes);

        if (pluginMessage == null) {
            return;
        }

        switch (pluginMessage.messageType()) {
            case PLAYER_JOIN, PLAYER_QUIT -> handlePlayerMessage(pluginMessage);
            default -> logger.warn("Ignoring plugin message with invalid message type");
        }
    }

    private void handlePlayerMessage(PluginMessage pluginMessage) {
        try {
            UUID playerId = pluginMessage.playerId();
            CarbonPlayer carbonPlayer = userManager.user(playerId).get();
            Party party = carbonPlayer.party().get();

            if (pluginMessage.messageType() == MessageType.PLAYER_JOIN) {
                Set<UUID> onlinePlayers = pluginMessage.onlinePlayers();
                if (onlinePlayers != null) {
                    onlinePlayers.forEach(playerManager::add);
                } else {
                    playerManager.add(playerId);
                }
            } else if (pluginMessage.messageType() == MessageType.PLAYER_QUIT) {
                playerManager.remove(playerId);
            }

            if (party != null) {
                scoreboardManager.updateScoreboard(playerId, party.members());
            }
        } catch (InterruptedException | ExecutionException | IllegalArgumentException e) {
            logger.error("Exception while handling plugin message", e);
        }
    }
}

