package com.king.hawkeye.router.channel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by King on 16/3/10.
 */
public class RouterQueue<T> {

    private static final Logger logger = LogManager.getLogger(DefaultRouterQueue.class);

    private BlockingQueue<T> queue = new LinkedBlockingQueue<T>();

    public RouterQueue() {}

    public RouterQueue(BlockingQueue<T> queue) {
        this.queue = queue;
    }
    public void add(T content) {
        queue.add(content);
    }

    public T take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            logger.error("interrupted while taking log from queue.");
            return null;
        }
    }

    public int size() {
        return queue.size();
    }

}
