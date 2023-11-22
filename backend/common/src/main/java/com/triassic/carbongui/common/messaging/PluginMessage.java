package com.triassic.carbongui.common.messaging;

import com.triassic.carbongui.common.enums.MessageType;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Set;
import java.util.UUID;

public record PluginMessage(
        MessageType messageType,
        UUID playerId,
        @Nullable Set<UUID> onlinePlayers
) {
}