package com.king.hawkeye.router.source;

import com.king.hawkeye.router.channel.AbstractRouterChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/4/16.
 */
public abstract class AbstractSource extends Thread {
    private static final Logger LOG = LogManager.getLogger(AbstractSource.class);

    protected AbstractRouterChannel channel;
    protected boolean isRunning = false;

    public abstract void source();

    public boolean isRunning() {
        return isRunning;
    }

    public void stopSource() {
        isRunning = false;
    }

    public AbstractSource(AbstractRouterChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            this.source();
        } catch (Exception e) {
            LOG.error("router source error, source thread will be stoped.", e);
        } finally {
            isRunning = false;
        }
    }

    @Override
    public synchronized void start() {
        isRunning = true;
        super.start();
    }
}
