package com.king.hawkeye.router.source;

import com.king.hawkeye.router.channel.AbstractRouterChannel;

/**
 * Created by King on 16/4/23.
 */
public class ChannelSource<T> extends AbstractSource {

    private AbstractRouterChannel<T> sourceChannel;

    public ChannelSource(AbstractRouterChannel<T> channel, AbstractRouterChannel<T> sourceChannel) {
        super(channel);
        this.sourceChannel = sourceChannel;
    }

    @Override
    public void source() {
        while (isRunning) {
            T source = sourceChannel.take();
            channel.put(source);
        }
    }
}
