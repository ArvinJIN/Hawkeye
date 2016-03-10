package com.king.hawkeye.watcher;

import java.io.IOException;

/**
 * Created by King on 16/3/10.
 */
public class Bootstrap {

    private static FileWatcher fileWatcher;

    private static ILogChannel channel;

    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/config/config.json";

    public Bootstrap() throws IOException {
        ConfigPaser configPaser = new ConfigPaser(CONFIG_FILE_PATH);
        configPaser.parseAndInit();
    }

    public static FileWatcher getFileWatcher() {
        return fileWatcher;
    }

    public static void setFileWatcher(FileWatcher fileWatcher) {
        Bootstrap.fileWatcher = fileWatcher;
    }

    public static ILogChannel getChannel() {
        return channel;
    }

    public static void setChannel(ILogChannel channel) {
        Bootstrap.channel = channel;
    }
}
