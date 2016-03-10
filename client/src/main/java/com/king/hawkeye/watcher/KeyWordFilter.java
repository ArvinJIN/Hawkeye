package com.king.hawkeye.watcher;

/**
 * 关键词 过滤器
 * Created by King on 16/3/10.
 */
public class KeyWordFilter extends LogFilter {

    private String keyWord;

    public KeyWordFilter(String keyWord) throws Exception {
        if(keyWord == null){
            throw new Exception("关键词不能为空.");
        }
        this.keyWord = keyWord.toUpperCase();
    }

    @Override
    public String _filter(String content) {
        return content.toUpperCase().contains(keyWord) ? content : null;
    }
}
