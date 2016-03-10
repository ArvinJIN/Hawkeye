package com.king.hawkeye.watcher;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by King on 16/3/10.
 */
public class FilterTest {

    @Test
    public void nullContent() {
        LogFilter filter = new BlankFilter();
        assertNull(filter.filter(null));
    }

    /* KeyWordFilter Test */

    @Test(expected = Exception.class)
    public void newInstanceWithNullKeyWord() throws Exception {
        new KeyWordFilter(null);
    }

    @Test
    public void newInstanceWithKeyWord() throws Exception {
        new KeyWordFilter("ERROR");
    }

    @Test
    public void filterContentByKeyWord() throws Exception {
        LogFilter logFilter = new KeyWordFilter("abc");
        String content = "I am the keyWord abc";
        String result = logFilter.filter(content);
        assertNotNull(result);
        assertEquals(content, result);

        content = "I have no keyword";
        result = logFilter.filter(content);
        assertNull(result);
    }


    /* LevelFilter Test */
    @Test(expected = Exception.class)
    public void newInstanceWithNoLevel() throws Exception {
        new LevelFilter(null);
    }

    @Test(expected = Exception.class)
    public void newInstanceByWrongLevel() throws Exception {
        new LevelFilter("abc");
    }

    @Test
    public void filterContentByLevel() throws Exception {
        LogFilter filter = new LevelFilter("INFO");
        assertNotNull(filter.filter("info 1"));
        assertNotNull(filter.filter("warn 2"));
        assertNotNull(filter.filter("error 3"));
        assertNotNull(filter.filter("fatal 4"));
        assertNull(filter.filter("debug 5"));
        assertNull(filter.filter("trace 6"));
    }

    @Test(expected = Exception.class)
    public void newInstanceByNullRegex() {
        new RegexFilter(null);
    }

    @Test
    public void filterContentByRegex() {
        LogFilter filter = new RegexFilter("^[0-9]+$");
        String content = "123456";
        String result = filter.filter(content);
        assertNotNull(result);
        assertEquals(content, result);

        content = "123abc";
        result = filter.filter(content);
        assertNull(result);
    }

}
