package com.king.hawkeye.router.channel;

import com.king.hawkeye.router.filter.AbstractFilterChain;

/**
 * 日志消息 管道
 * Created by King on 16/3/10.
 */
public abstract class AbstractRouterChannel<T> {

    protected AbstractFilterChain<T> abstractFilterChain;

    protected RouterQueue<T> queue;

    public AbstractRouterChannel(AbstractFilterChain abstractFilterChain, RouterQueue queue) {
        this.abstractFilterChain = abstractFilterChain;
        this.queue = queue;
    }

    public void put(T t) {
        T result = abstractFilterChain.filter(t);
        if (result == null) {
            return;
        }
        queue.add(t);
    }

    public T take() {
        return queue.take();
    }


    public AbstractFilterChain getFilterChain() {
        return abstractFilterChain;
    }

    public void setAbstractFilterChain(AbstractFilterChain abstractFilterChain) {
        this.abstractFilterChain = abstractFilterChain;
    }

    public RouterQueue getLogQueue() {
        return queue;
    }

    public void setLogQueue(RouterQueue logQueue) {
        this.queue = logQueue;
    }

}
