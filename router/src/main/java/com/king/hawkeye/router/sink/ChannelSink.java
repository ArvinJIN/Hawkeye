package com.king.hawkeye.router.sink;

import com.king.hawkeye.router.channel.AbstractRouterChannel;
import com.king.hawkeye.router.source.AbstractSource;

/**
 * Created by King on 16/4/23.
 */
public class ChannelSink<T> extends AbstractSink<T> {
    private AbstractRouterChannel<T> sinkChannel;

    public ChannelSink(AbstractRouterChannel sinkChannel) {
        this.sinkChannel = sinkChannel;
    }

    @Override
    public void sink(T t) {
        sinkChannel.put(t);
    }
}
