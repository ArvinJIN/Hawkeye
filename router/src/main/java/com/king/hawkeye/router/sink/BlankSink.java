package com.king.hawkeye.router.sink;

import com.king.hawkeye.router.channel.AbstractRouterChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/3/11.
 */
public class BlankSink extends AbstractSink<String> {
    private static final Logger logger = LogManager.getLogger(BlankSink.class);

    @Override
    public void sink(String s) {
        System.out.println("alert: " + s);
    }
}
