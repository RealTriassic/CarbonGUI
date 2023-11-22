package com.triassic.carbongui.common.manager;

import com.triassic.carbongui.common.Platform;
import com.triassic.carbongui.common.configuration.Configuration;
import com.triassic.carbongui.common.configuration.ConfigurationContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.*;
import java.util.stream.Collectors;

import static com.triassic.carbongui.common.util.ColorUtils.parseMiniMessage;

public final class ScoreboardManager {

    private final Platform platform;
    private final PlayerManager playerManager;

    private final Component SCOREBOARD_TITLE;
    private static final NamedTextColor ONLINE_COLOR = NamedTextColor.GREEN;
    private static final NamedTextColor OFFLINE_COLOR = NamedTextColor.DARK_GRAY;

    public ScoreboardManager(
            final Platform platform,
            final PlayerManager playerManager,
            final ConfigurationContainer<Configuration> config
    ) {
        this.platform = platform;
        this.playerManager = playerManager;
        this.SCOREBOARD_TITLE = parseMiniMessage(config.get().getScoreboardSettings().getScoreboardTitle());
    }

    /**
     * Update the scoreboard for the provided set of party members.
     *
     * @param playerId The UUID of the player that caused the scoreboard update.
     * @param partyMembers The set of party members to update the scoreboard for.
     */

    public void updateScoreboard(final UUID playerId, final Set<UUID> partyMembers) {
        if (!(partyMembers.contains(playerId) || playerManager.online(playerId)))
            platform.deleteScoreboard(playerId);

        if (!partyMembers.isEmpty()) {
            final List<Component> scoreboardLines = getUpdatedScoreboardLines(partyMembers);

            partyMembers.stream()
                    .filter(playerManager::online)
                    .forEach(player -> platform.updateScoreboard(player, SCOREBOARD_TITLE, scoreboardLines));
        }
    }

    /**
     * Retrieve the updated scoreboard lines for the provided set of party members.
     *
     * @param partyMembers The set of party members to generate the scoreboard lines for.
     * @return The list of updated scoreboard lines as TextComponents.
     */

    private List<Component> getUpdatedScoreboardLines(final Set<UUID> partyMembers) {
        return partyMembers.stream()
                .map(playerId -> {
                    final String username = platform.username(playerId);
                    final boolean isOnline = playerManager.online(playerId);
                    return Component.text(Objects.requireNonNull(username), isOnline ? ONLINE_COLOR : OFFLINE_COLOR);
                })
                .sorted(Comparator.comparingInt(component -> component.color() == ONLINE_COLOR ? 0 : 1))
                .collect(Collectors.toList());
    }
}