package com.king.hawkeye.router.sink;

import com.king.hawkeye.router.channel.AbstractRouterChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by King on 16/4/22.
 */
public class SinkChain<T> extends Thread {

    private static final Logger logger = LogManager.getLogger(AbstractSink.class);
    protected AbstractRouterChannel<T> channel;
    private List<AbstractSink<T>> sinks = new LinkedList<>();

    protected boolean isWatching = false;

    public SinkChain(AbstractRouterChannel<T> channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        isWatching = true;
        while (isWatching) {
            try {
                T t = channel.take();
                sink(t);
            } catch (Exception e) {
                logger.error("router sink error, sink thread will be stoped", e);
                this.isWatching = false;
            }
        }
    }

    public void sink(T t) {
        for (AbstractSink<T> sink : sinks) {
            sink.sink(t);
        }
    }

    public void stopWatching() {
        this.isWatching = false;
    }

    public void addSink(AbstractSink sink) {
        if(sink == null){
            return;
        }
        sinks.add(sink);
    }
}
