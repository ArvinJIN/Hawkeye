package com.king.hawkeye.remote.handler;

import com.king.hawkeye.remote.core.ClientCollector;
import com.king.hawkeye.remote.protocal.Header;
import com.king.hawkeye.remote.protocal.MessageType;
import com.king.hawkeye.remote.protocal.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by King on 16/3/31.
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {
    private static final Logger LOG = LogManager.getLogger(LoginAuthRespHandler.class);

    private String[] whiteList = {"localhost"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()) {
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            String host =  address.getHostName();
            int port = address.getPort();
            String hostAndPort = host + ":" + port;
            NettyMessage loginResp = null;
            if (ClientCollector.isRegisted(hostAndPort)) {
                loginResp = buildResponse((byte) -1);
            } else {
                boolean isOK = false;
                for (String WIP : whiteList) {
                    if (WIP.equals(host)) {
                        isOK = true;
                        break;
                    }
                }
                loginResp = isOK ? buildResponse((byte) 0) : buildResponse((byte) -1);
                if (isOK) {
                    String projectName = (String) message.getBody();
                    LOG.info("client regist : " + hostAndPort + " " + projectName);
                    ClientCollector.regist(hostAndPort, projectName, ctx.channel());
                } else {
                    LOG.info("client connect refused : " + hostAndPort);
                }
            }
            System.out.println("The login response is : " + loginResp + "body [ " + loginResp.getBody() + " ]");
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        String host =  address.getHostName();
        int port = address.getPort();
        String hostAndPort = host + ":" + port;
        ClientCollector.remove(hostAndPort);
        LOG.info("remove client : " + hostAndPort);
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
