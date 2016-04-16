package com.king.hawkeye.remote.protocal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.Map;

/**
 * Created by King on 16/3/31.
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    private KryoEncoder kryoEncoder;

    public NettyMessageEncoder() {
        kryoEncoder = new KryoEncoder();
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, List<Object> list) throws Exception {
        if (nettyMessage == null || nettyMessage.getHeader() == null) {
            throw new Exception("The encode message is null.");
        }
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(nettyMessage.getHeader().getCrcCode());
        sendBuf.writeInt(nettyMessage.getHeader().getLength());
        sendBuf.writeLong(nettyMessage.getHeader().getSessionID());
        sendBuf.writeByte(nettyMessage.getHeader().getType());
        sendBuf.writeByte(nettyMessage.getHeader().getPriority());
        sendBuf.writeInt(nettyMessage.getHeader().getAttachment().size());


        String key = null;
        byte[] keyArray = null;
        Object value = null;
        byte[] valueArray = null;

        for (Map.Entry<String, Object> param : nettyMessage.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("utf-8");
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);

            kryoEncoder.encode(param.getValue(), sendBuf);
        }
        key = null;
        keyArray = null;
        value = null;
        valueArray = null;

        if (nettyMessage.getBody() != null) {
            kryoEncoder.encode(nettyMessage.getBody(), sendBuf);
        }
        int readable = sendBuf.readableBytes();
        sendBuf.setInt(4, readable);
        list.add(sendBuf);
    }
}
