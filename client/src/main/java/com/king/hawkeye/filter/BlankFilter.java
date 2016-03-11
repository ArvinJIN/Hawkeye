package com.king.hawkeye.filter;

/**
 * Created by King on 16/3/10.
 */
public class BlankFilter extends LogFilter {
    @Override
    public String _filter(String content) {
        return content;
    }
}
