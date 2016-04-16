package com.king.hawkeye.watcher;

import com.king.hawkeye.client.config.ConfigPaser;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by King on 16/3/10.
 */
public class HawkeyeConfigParserTest {

    String path = System.getProperty("user.dir") + "/config/config.json";

    @Test
    public void test() throws IOException {
        ConfigPaser configPaser = new ConfigPaser(path);
        configPaser.parseAndInit();
    }

}
