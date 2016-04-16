package com.king.hawkeye.remote;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by King on 16/3/29.
 */
public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
    protected void encode(ChannelHandlerContext channelHandlerContext, UnixTime unixTime, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(unixTime.value());
    }
}
