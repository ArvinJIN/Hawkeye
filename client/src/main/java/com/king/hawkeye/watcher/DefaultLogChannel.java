package com.king.hawkeye.watcher;

/**
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
        String result = filterChain.filter(content);
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
