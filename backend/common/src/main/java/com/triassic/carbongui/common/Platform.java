package com.triassic.carbongui.common;

import net.kyori.adventure.text.Component;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;

@DefaultQualifier(NotNull.class)
public interface Platform {

    /**
     * Retrieves the username of the player with the specified UUID.
     *
     * @param playerId the UUID of the player.
     * @return the username of the player.
     */

    @Nullable
    String username(UUID playerId);

    /**
     * Updates the scoreboard for the player with the <br>
     * specified UUID using the provided scoreboard lines.
     *
     * @param playerId the UUID of the player.
     * @param title    the title for the scoreboard
     * @param lines    the lines to update the scoreboard with.
     */

    void updateScoreboard(UUID playerId, Component title, List<Component> lines);

    /**
     * Removes the scoreboard for the player with the specified UUID.
     *
     * @param playerId the UUID of the player.
     */

    void deleteScoreboard(UUID playerId);

    /**
     * Retrieves the logger for the platform.
     *
     * @return the logger for the platform.
     */

    Logger logger();

    /**
     * Checks whether the platform is running as a proxy.
     *
     * @return {@code true} if the platform is running as a proxy, {@code false} otherwise.
     */

    boolean isProxy();
}
