package com.king.hawkeye.router.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则 过滤器
 * Created by King on 16/3/10.
 */
public class RegexFilter extends AbstractFilter {

    private Pattern pattern;

    public RegexFilter(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public String _filter(String content) {
        Matcher matcher = pattern.matcher(content);
        return matcher.matches() ? content : null;
    }
}
