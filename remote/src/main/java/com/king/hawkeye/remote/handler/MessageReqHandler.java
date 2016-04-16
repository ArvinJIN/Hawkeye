package com.king.hawkeye.remote.handler;

import com.king.hawkeye.remote.core.ClientCollector;
import com.king.hawkeye.remote.core.ProcessHandler;
import com.king.hawkeye.remote.core.RemoteInfo;
import com.king.hawkeye.remote.protocal.MessageType;
import com.king.hawkeye.remote.protocal.NettyMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

/**
 * Created by King on 16/3/31.
 */
public class MessageReqHandler extends ChannelHandlerAdapter {

    private static final Logger LOG = LogManager.getLogger(MessageReqHandler.class);

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
            String messageBody = (String) message.getBody();
            String hostAndPort = getHostAndPort(ctx.channel());
            String projectName = ClientCollector.getProjectName(hostAndPort);
            String clientName = ClientCollector.getClientName(hostAndPort);
            LOG.info("remote message : " + new RemoteInfo(projectName, clientName, hostAndPort, messageBody));
            handler.process(new RemoteInfo(projectName, clientName, hostAndPort, messageBody));
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    private String getHostAndPort(Channel channel) {
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
        String host =  address.getHostName();
        int port = address.getPort();
        return host + ":" + port;
    }
}
