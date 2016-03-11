package com.king.hawkeye.channel;

import com.king.hawkeye.filter.FilterChain;

/**
 * 默认日志消息管道 （过滤）
 * Created by King on 16/3/10.
 */
public class DefaultLogChannel implements ILogChannel {

    private FilterChain filterChain;

    private ILogQueue logQueue;

    public DefaultLogChannel(){
        filterChain = new FilterChain();
        logQueue = new LogQueue();
    }

    @Override
    public void put(String content) {
//        System.out.println("before filter chain: " + content);
        String result = filterChain.filter(content);
//        System.out.println("after filter chain: " + result);
        if(result == null){
            return;
        }
        logQueue.add(content);
    }

    @Override
    public String take() {
        return logQueue.take();
    }

    public FilterChain getFilterChain() {
        return filterChain;
    }

    public void setFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public ILogQueue getLogQueue() {
        return logQueue;
    }

    public void setLogQueue(ILogQueue logQueue) {
        this.logQueue = logQueue;
    }
}
