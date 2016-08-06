package com.king.hawkeye.router.source;

import com.king.hawkeye.router.channel.AbstractRouterChannel;

/**
 * Created by King on 16/4/23.
 */
public class TestSource extends AbstractSource {

    private int scan;
    private String log;

    public TestSource(AbstractRouterChannel channel, int scan, String log) {
        super(channel);
        this.scan = scan;
        this.log = log;
    }

    @Override
    public void source() {
        isRunning = true;
        while (isRunning) {
            try {
                Thread.sleep(scan);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.put(log);
        }
    }
}
