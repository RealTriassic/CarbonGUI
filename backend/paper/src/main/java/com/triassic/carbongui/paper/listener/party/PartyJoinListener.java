package com.triassic.carbongui.paper.listener.party;

import com.triassic.carbongui.common.manager.ScoreboardManager;
import net.draycia.carbon.api.event.events.PartyJoinEvent;
import net.draycia.carbon.api.users.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public final class PartyJoinListener implements Listener {

    private final ScoreboardManager scoreboardManager;

    public PartyJoinListener(
            final ScoreboardManager scoreboardManager
    ) {
        this.scoreboardManager = scoreboardManager;
    }

    public void onPartyJoin(final PartyJoinEvent event) {
        final Player player = Bukkit.getPlayer(event.playerId());

        if (player != null) {
            final Party party = event.party();
            scoreboardManager.updateScoreboard(event.playerId(), party.members());
        }
    }
}
