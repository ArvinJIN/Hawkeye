package com.king.hawkeye.router.filter;

/**
 * Created by King on 16/3/10.
 */
public class BlankFilter extends AbstractFilter {
    @Override
    public String _filter(String content) {
        return content;
    }
}
