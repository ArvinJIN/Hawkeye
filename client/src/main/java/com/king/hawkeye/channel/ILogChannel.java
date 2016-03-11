package com.king.hawkeye.channel;

/**
 * 日志消息 管道
 * Created by King on 16/3/10.
 */
public interface ILogChannel {

    void put(String content);

    String take();

}
