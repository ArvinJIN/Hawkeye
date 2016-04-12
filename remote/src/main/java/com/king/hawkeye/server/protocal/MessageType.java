package com.king.hawkeye.server.protocal;

/**
 * Created by King on 16/3/31.
 */
public enum MessageType {
    LOGIN_RESP((byte) 4),
    LOGIN_REQ((byte) 3),
    HEARTBEAT_REQ((byte) 5),
    HEARTBEAT_RESP((byte) 6),
    MESSAGE_REQ((byte) 7),
    MESSAGE_RESP((byte) 8);
    private byte value;

    private MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return value;
    }
}
