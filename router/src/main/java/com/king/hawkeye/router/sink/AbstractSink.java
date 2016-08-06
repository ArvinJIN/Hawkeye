package com.king.hawkeye.router.sink;

import com.king.hawkeye.router.channel.AbstractRouterChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志信息管道 监控器
 * Created by King on 16/3/11.
 */
public abstract class AbstractSink<T> {
    public abstract void sink(T t);
}
