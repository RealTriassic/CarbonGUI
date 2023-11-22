package com.triassic.carbongui.paper;

import com.triassic.carbongui.common.CarbonGUI;
import com.triassic.carbongui.common.Platform;
import com.triassic.carbongui.common.manager.ScoreboardManager;
import com.triassic.carbongui.paper.listener.input.MessageListener;
import com.triassic.carbongui.paper.listener.party.PartyJoinListener;
import com.triassic.carbongui.paper.listener.party.PartyLeaveListener;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.draycia.carbon.api.CarbonChat;
import net.draycia.carbon.api.CarbonChatProvider;
import net.draycia.carbon.api.event.events.PartyJoinEvent;
import net.draycia.carbon.api.event.events.PartyLeaveEvent;
import net.draycia.carbon.api.users.UserManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class CarbonGUIPaper extends JavaPlugin implements Platform {

    public static final String CHANNEL = "carbongui:main";

    private final Logger logger = this.getSLF4JLogger();
    private static final Map<UUID, FastBoard> boards = new HashMap<>();

    private CarbonGUI instance;
    private CarbonChat carbon;
    private UserManager<?> userManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        instance = new CarbonGUI(this, getDataFolder().toPath());
        scoreboardManager = instance.scoreboardManager();

        carbon = CarbonChatProvider.carbonChat();
        userManager = carbon.userManager();

        registerListeners();
    }

    private void registerListeners() {
        carbon.eventHandler().subscribe(PartyJoinEvent.class, 0, false, new PartyJoinListener(scoreboardManager)::onPartyJoin);
        carbon.eventHandler().subscribe(PartyLeaveEvent.class, 0, false, new PartyLeaveListener(scoreboardManager)::onPartyLeave);

        getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL,
                new MessageListener(logger, userManager, instance.playerManager(), scoreboardManager)
        );
    }

    @Override
    public @Nullable String username(final @NotNull UUID playerId) {
        return getServer().getOfflinePlayer(playerId).getName();
    }

    @Override
    public void updateScoreboard(
            final @NotNull UUID playerId,
            final @NotNull Component title,
            final @NotNull List<Component> lines
    ) {
        final Player player = getServer().getPlayer(playerId);
        if (player != null) {
            CompletableFuture.runAsync(() -> {
                FastBoard scoreboard = boards.computeIfAbsent(playerId, id -> new FastBoard(player));
                scoreboard.updateTitle(title);
                scoreboard.updateLines(lines);
            });
        }
    }

    @Override
    public void deleteScoreboard(final @NotNull UUID playerId) {
        boards.computeIfPresent(playerId, (id, scoreboard) -> {
            scoreboard.delete();
            return null;
        });
    }

    @Override
    public @NotNull Logger logger() {
        return this.logger;
    }

    @Override
    public boolean isProxy() {
        return false;
    }
}
