package com.king.hawkeye.server.handler;

import com.king.hawkeye.server.core.ProcessHandler;
import com.king.hawkeye.server.protocal.MessageType;
import com.king.hawkeye.server.protocal.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by King on 16/3/31.
 */
public class MessageReqHandler extends ChannelHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(MessageReqHandler.class);

    private ProcessHandler handler;

    public MessageReqHandler(ProcessHandler handler) {
        this.handler = handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg : " + msg);
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.MESSAGE_REQ.value()) {
            System.out.println("get client message : " + message.getBody());
            handler.process(message.getBody());
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
