package com.king.hawkeye.watcher;

import com.king.hawkeye.filter.FilterChain;
import com.king.hawkeye.filter.KeyWordFilter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by King on 16/3/10.
 */
public class FilterChainTest {
    FilterChain filterChain;

    @Before
    public void before() {
        filterChain = new FilterChain();
    }

    @Test
    public void filterWithKeyordFilter() throws Exception {
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new KeyWordFilter("keyword"));
        String content = "keyword ..";
        String result = filterChain.filter(content);
        assertNotNull(result);
        assertEquals(content, result);

        content = "abc ..";
        result = filterChain.filter(content);
        assertNull(result);
    }
    @Test
    public void filterWithNullContent() throws Exception {
        filterChain.addFilter(new KeyWordFilter("keyword"));
        assertNull(filterChain.filter(null));
    }

}
