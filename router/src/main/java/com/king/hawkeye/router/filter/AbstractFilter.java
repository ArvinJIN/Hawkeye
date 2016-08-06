package com.king.hawkeye.router.filter;

/**
 * 过滤器 基类
 * Created by King on 16/3/10.
 */
public abstract class AbstractFilter {
    public String filter(String content){
        if(content == null){
            return null;
        }
        return _filter(content);
    }

    protected abstract String _filter(String content);
}
