package com.king.hawkeye.router;

import com.king.hawkeye.router.channel.AbstractRouterChannel;
import com.king.hawkeye.router.sink.AbstractSink;
import com.king.hawkeye.router.sink.SinkChain;
import com.king.hawkeye.router.source.AbstractSource;

/**
 * Created by King on 16/4/22.
 */
public class Router<T> {
    private AbstractSource source;

    private AbstractRouterChannel<T> channel;

    private SinkChain<T> sinkChain;

    public Router(AbstractSource source, AbstractRouterChannel channel, AbstractSink... sinks)  throws Exception{
        if(source == null) {
            throw new Exception("没有设置source.");
        }
        if(channel == null) {
            throw new Exception("没有设置channel.");
        }
        if(sinks == null) {
            throw new Exception("没有添加sink.");
        }

        this.source = source;
        this.channel = channel;

        sinkChain = new SinkChain<T>(channel);
        for (AbstractSink sink : sinks) {
            sinkChain.addSink(sink);
        }
    }

    public void start() {
        source.start();
        sinkChain.start();
    }

    public void stop() {
        source.stopSource();
        sinkChain.stopWatching();
    }

    public AbstractSource getSource() {
        return source;
    }

    public void setSource(AbstractSource source) {
        this.source = source;
    }

    public AbstractRouterChannel getChannel() {
        return channel;
    }

    public void setChannel(AbstractRouterChannel channel) {
        this.channel = channel;
    }

    public SinkChain getSinkChain() {
        return sinkChain;
    }

    public void setSinkChain(SinkChain sinkChain) {
        this.sinkChain = sinkChain;
    }
}
