package com.king.hawkeye.watcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by King on 16/3/10.
 */
public class FilterChain {

    private Logger logger = LogManager.getLogger(FilterChain.class);

    private List<LogFilter> filters = new ArrayList<>();

    public FilterChain addFilter(LogFilter filter){
        filters.add(filter);
        return this;
    }

    public String filter(String content){
        logger.warn("filter chain has no filter added!");
        if(content == null){
            return null;
        }
        Iterator<LogFilter> it = filters.iterator();
        while (it.hasNext()){
            String result = it.next().filter(content);
            if(result == null){
                return null;
            }
        }
        return content;
    }

}
