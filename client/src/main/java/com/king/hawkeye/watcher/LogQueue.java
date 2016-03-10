package com.king.hawkeye.watcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by King on 16/3/10.
 */
public class LogQueue implements ILogQueue {

    private static final Logger logger = LogManager.getLogger("LogQueue");

    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void add(String content) {
        queue.add(content);
    }

    public String take() {
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
