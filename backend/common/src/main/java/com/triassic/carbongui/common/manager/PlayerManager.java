package com.triassic.carbongui.common.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class PlayerManager {

    private final Set<UUID> onlinePlayers;

    public PlayerManager() {
        this.onlinePlayers = new HashSet<>();
    }

    /**
     * Adds a player to the online players set.
     *
     * @param uuid the UUID of the player to be added.
     */

    public void add(final UUID uuid) {
        onlinePlayers.add(uuid);
    }

    /**
     * Removes a player from the online players set.
     *
     * @param uuid the UUID of the player to be removed.
     */

    public void remove(final UUID uuid) {
        onlinePlayers.remove(uuid);
    }

    /**
     * Checks if a player with the given UUID is online.
     *
     * @param uuid the UUID of the player to check.
     * @return true if the player is online, otherwise false.
     */

    public boolean online(final UUID uuid) {
        return onlinePlayers.contains(uuid);
    }
}
