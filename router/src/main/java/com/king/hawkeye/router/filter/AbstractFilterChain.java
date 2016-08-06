package com.king.hawkeye.router.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 过滤器链
 * Created by King on 16/3/10.
 */
public abstract class AbstractFilterChain<T> {

    private Logger logger = LogManager.getLogger(AbstractFilterChain.class);

    private List<AbstractFilter> filters = new ArrayList<>();

    public AbstractFilterChain addFilter(AbstractFilter filter){
        filters.add(filter);
        return this;
    }

    public T filter(T content){
        if(content == null){
            return null;
        }
        String filterContent = getFilterContent(content);
        Iterator<AbstractFilter> it = filters.iterator();
        while (it.hasNext()){
            String result = it.next().filter(filterContent);
            if(result == null){
                return null;
            }
        }
        return content;
    }

    public abstract String getFilterContent(T t);

}
