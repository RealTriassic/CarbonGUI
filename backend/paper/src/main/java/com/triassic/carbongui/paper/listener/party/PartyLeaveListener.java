package com.triassic.carbongui.paper.listener.party;

import com.triassic.carbongui.common.manager.ScoreboardManager;
import net.draycia.carbon.api.event.events.PartyLeaveEvent;
import net.draycia.carbon.api.users.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public final class PartyLeaveListener implements Listener {

    private final ScoreboardManager scoreboardManager;

    public PartyLeaveListener(
            final ScoreboardManager scoreboardManager
    ) {
        this.scoreboardManager = scoreboardManager;
    }

    public void onPartyLeave(final PartyLeaveEvent event) {
        final Player player = Bukkit.getPlayer(event.playerId());

        if (player != null) {
            final Party party = event.party();
            scoreboardManager.updateScoreboard(event.playerId(), party.members());
        }
    }
}
