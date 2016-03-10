package com.king.hawkeye.watcher;

/**
 * Created by King on 16/3/10.
 */
public interface ILogQueue {

    void add(String content);

    String take();

    int size();

}
