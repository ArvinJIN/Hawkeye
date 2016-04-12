package com.king.hawkeye.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by King on 16/3/29.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.writeAndFlush("response: " + msg);
//    }


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channel connect: host: " + ctx.channel().remoteAddress());
        final ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        System.out.println("client channel close..");
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
