package com.triassic.carbongui.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
    PLAYER_JOIN((byte) 0),
    PLAYER_QUIT((byte) 1);

    private static final Map<Byte, MessageType> messageTypeMap = new HashMap<>();

    static {
        for (MessageType messageType : values()) {
            messageTypeMap.put(messageType.value, messageType);
        }
    }

    private final byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public static MessageType fromByte(byte value) {
        return messageTypeMap.get(value);
    }

    public byte getByte() {
        return value;
    }
}