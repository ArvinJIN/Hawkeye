package com.king.hawkeye.server.core;

import com.king.hawkeye.server.handler.HeartBeatReqHandler;
import com.king.hawkeye.server.handler.LoginAuthReqHandler;
import com.king.hawkeye.server.protocal.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by King on 16/3/31.
 */
public class NettyClient {
    private static final Log LOG = LogFactory.getLog(NettyClient.class);

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private EventLoopGroup group = new NioEventLoopGroup();

    private Channel channel;

    private int clientPort;

    public NettyClient() {
    }

    public NettyClient(int clientPort) {
        this.clientPort = clientPort;
    }

    public void connect(int port, String host) throws Exception {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
                            ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(500));
                            ch.pipeline().addLast("LoginAuthHandler", new LoginAuthReqHandler());
                            ch.pipeline().addLast("HeartBeatHandler", new HeartBeatReqHandler());
                        }
                    });


            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port), new InetSocketAddress("127.0.0.1", clientPort)).sync();
            channel = future.channel();
//            future.addListener(new ChannelFutureListener() {
//                public void operationComplete(ChannelFuture future) throws Exception {
//                    LOG.info("future listener : channel close..");
//                    future.channel().closeFuture().sync();
//                }
//            });

        } catch (Exception e) {
            LOG.error(e);
            throw e;
        } finally {
//            group.shutdownGracefully();
//
//            //发起重连
//            executor.execute(new Runnable() {
//                public void run() {
//                    try {
//                        TimeUnit.SECONDS.sleep(5);
//
//                        connect(NettyConstant.SERVER_PORT, NettyConstant.SERVER_IP);
//
//                    } catch (EncoderException e) {
//                        LOG.error(e);
//                    } catch (InterruptedException e) {
//                        LOG.error(e);
//                    } catch (Exception e) {
//                        LOG.error(e);
//                    }
//                }
//            });
        }
    }

    public void disConnect() throws InterruptedException {
        channel.closeFuture().sync();
    }

    public boolean send(Object obj) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.MESSAGE_REQ.value());
        message.setHeader(header);
        message.setBody(obj);
        System.out.println("message : " + message);

        ChannelFuture future = channel.writeAndFlush(message);

        return true;
    }

    public Channel getChannel() {
        return channel;
    }

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.connect(9527, "127.0.0.1");

        client.send("I am king...");

        Thread.sleep(60000);

        System.out.println("sleep end..");

        client.channel.closeFuture().sync();

    }
}
