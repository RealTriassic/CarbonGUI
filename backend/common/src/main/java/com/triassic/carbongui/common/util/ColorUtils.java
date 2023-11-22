package com.triassic.carbongui.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class ColorUtils {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    /**
     * Parses colours from a provided string into MiniMessage format.
     *
     * @param input the MiniMessage input
     * @return the parsed MiniMessage
     */

    public static Component parseMiniMessage(final String input) {
        return mm.deserialize(input);
    }
}
