package com.king.hawkeye.watcher;

/**
 * Created by King on 16/3/10.
 */
public interface ILogChannel {

    void put(String content);

    String take();

}
