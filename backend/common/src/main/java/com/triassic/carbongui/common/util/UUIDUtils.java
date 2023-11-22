package com.triassic.carbongui.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.UUID;

public final class UUIDUtils {

    private static final Gson gson = new Gson();
    private static final Type setType = new TypeToken<Set<UUID>>() {}.getType();

    /**
     * Converts a set of UUIDs to a byte array.
     *
     * @param uuidSet the set of UUIDs to be converted
     * @return the byte array representing the set of UUIDs
     */

    public static byte[] setToBytes(final Set<UUID> uuidSet) {
        final String json = gson.toJson(uuidSet, setType);
        return json.getBytes();
    }

    /**
     * Converts a single UUID to a byte array.
     *
     * @param uuid the UUID to be converted
     * @return the byte array representing the UUID
     */

    public static byte[] uuidToBytes(final UUID uuid) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    /**
     * Converts a byte array to a set of UUIDs.
     *
     * @param bytes the byte array to be converted
     * @return the set of UUIDs reconstructed from the byte array
     */

    public static Set<UUID> setFromBytes(final byte[] bytes) {
        final String json = new String(bytes);
        return gson.fromJson(json, setType);
    }

    /**
     * Reconstructs a UUID from a byte array.
     *
     * @param bytes the byte array representing the UUID
     * @return the UUID reconstructed from the byte array
     */

    public static UUID uuidFromBytes(final byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}

