package com.king.hawkeye.router.filter;

/**
 * 关键词 过滤器
 * Created by King on 16/3/10.
 */
public class KeyWordFilter extends AbstractFilter {

    private String keyWord;

    public KeyWordFilter(String keyWord) throws Exception {
        if(keyWord == null){
            throw new Exception("关键词不能为空.");
        }
        this.keyWord = keyWord.toLowerCase();
    }

    @Override
    public String _filter(String content) {
        return content.toLowerCase().contains(keyWord) ? content : null;
    }
}
