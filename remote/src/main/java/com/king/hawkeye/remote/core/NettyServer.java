package com.king.hawkeye.remote.core;

import com.king.hawkeye.remote.handler.HeartBeatRespHandler;
import com.king.hawkeye.remote.handler.LoginAuthRespHandler;
import com.king.hawkeye.remote.handler.MessageReqHandler;
import com.king.hawkeye.remote.protocal.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/3/31.
 */
public class NettyServer {

    private static final Logger LOG = LogManager.getLogger(NettyServer.class);

    private int port = 9527;

    private ProcessHandler handler;

    private Channel channel;

    public NettyServer(int port, ProcessHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ReadTimeoutHandler(60));
                        ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
                        ch.pipeline().addLast(new NettyMessageEncoder());
                        ch.pipeline().addLast(new LoginAuthRespHandler());
                        ch.pipeline().addLast("HeartBeatHandler", new HeartBeatRespHandler());
                        ch.pipeline().addLast("MessageReqHandler", new MessageReqHandler(handler));
                    }
                });
        ChannelFuture future = bootstrap.bind(port).sync();
        System.out.println("netty remote started : port = " +  port);
        channel = future.channel();
//        future.channel().closeFuture().sync();
    }

    public boolean send(String hostAndPort, Object obj) {
        Channel clientChannel = ClientCollector.getChannel(hostAndPort);

        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.MESSAGE_REQ.value());
        message.setHeader(header);
        message.setBody(obj);
        System.out.println("message : " + message);

        ChannelFuture future = clientChannel.writeAndFlush(message);

        return true;
    }

    public static void main(String[] args) throws InterruptedException {
//        new NettyServer(9527).start();
    }
}
