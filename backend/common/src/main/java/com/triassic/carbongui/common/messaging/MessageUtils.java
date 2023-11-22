package com.triassic.carbongui.common.messaging;

import com.triassic.carbongui.common.enums.MessageType;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.*;
import java.util.Set;
import java.util.UUID;

import static com.triassic.carbongui.common.util.UUIDUtils.*;

public final class MessageUtils {

    /**
     * Creates a plugin message from the provided parameters.
     *
     * @param messageType   The type of the message.
     * @param playerId      The UUID of the player.
     * @param onlinePlayers The set of online players.
     * @return A byte array representing the plugin message.
     */

    public static byte[] createPluginMessage(
            final MessageType messageType,
            final UUID playerId,
            @Nullable final Set<UUID> onlinePlayers
    ) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream();
             final DataOutputStream buf = new DataOutputStream(bos)) {

            buf.writeByte(messageType.getByte());
            buf.write(uuidToBytes(playerId));

            if (onlinePlayers != null) {
                buf.write(setToBytes(onlinePlayers));
            }

            return bos.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    /**
     * Reads a plugin message from a byte array.
     *
     * @param bytes The byte array representing the plugin message.
     * @return A PluginMessage object.
     */

    public static PluginMessage readPluginMessage(final byte[] bytes) {
        try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             final DataInputStream buf = new DataInputStream(bis)) {

            final MessageType messageType = MessageType.fromByte(buf.readByte());
            final UUID playerId = uuidFromBytes(buf.readNBytes(16));

            Set<UUID> onlinePlayers = null;
            if (bis.available() > 0) {
                final byte[] serializedData = new byte[bis.available()];
                buf.readFully(serializedData);
                onlinePlayers = setFromBytes(serializedData);
            }

            return new PluginMessage(messageType, playerId, onlinePlayers);
        } catch (IOException e) {
            return null;
        }
    }
}
