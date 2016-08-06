package com.king.hawkeye.router.channel;


import com.king.hawkeye.router.filter.AbstractFilter;
import com.king.hawkeye.router.filter.AbstractFilterChain;

/**
 * 默认日志消息管道 （过滤）
 * Created by King on 16/3/10.
 */
public class DefaultRouterChannel extends AbstractRouterChannel<String> {

    public DefaultRouterChannel() {
        super(new AbstractFilterChain<String>() {
            @Override
            public String getFilterContent(String s) {
                return s;
            }
        }, new DefaultRouterQueue());
    }

}
