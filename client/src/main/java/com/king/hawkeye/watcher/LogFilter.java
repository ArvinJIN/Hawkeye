package com.king.hawkeye.watcher;

/**
 * Created by King on 16/3/10.
 */
public abstract class LogFilter {
    public String filter(String content){
        if(content == null){
            return null;
        }
        return _filter(content);
    }

    protected abstract String _filter(String content);
}
