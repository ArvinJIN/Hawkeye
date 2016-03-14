package com.king.hawkeye.watcher;

import com.king.hawkeye.alert.BlankAlert;
import com.king.hawkeye.alert.EmailAlert;
import com.king.hawkeye.alert.ILogAlert;
import com.king.hawkeye.channel.DefaultLogChannel;
import com.king.hawkeye.channel.ILogChannel;
import com.king.hawkeye.config.ConfigPaser;
import com.king.hawkeye.config.HawkeyeConfig;
import com.king.hawkeye.filter.FilterChain;
import com.king.hawkeye.filter.KeyWordFilter;
import com.king.hawkeye.filter.LevelFilter;
import com.king.hawkeye.filter.RegexFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Hawkeye 入口
 * Created by King on 16/3/10.
 */
public class Bootstrap {

    private static final Logger logger = LogManager.getLogger(Bootstrap.class);

    private static HawkeyeConfig config;

    private static FileWatcher fileWatcher;

    private static ChannelWatcher channelWatcher;

    private static ILogChannel channel;

    private static ILogAlert logAlert;

    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/config/config.json";
    private static boolean isRunning = true;

    private Bootstrap() {}

    public static void main(String[] args) {
        try {
            Bootstrap.init();
        } catch (Exception e) {
            logger.error("Hawkeye init error.", e);
            return;
        }
        System.out.println("Hawkeye init...");
        Bootstrap.start();
    }

    public static void init() throws Exception {
        readConfigAndInit();
        initChannel();
        initAlert();
        initFileWatcher();
        initChannelWatcher();
    }

    private static void initChannelWatcher() {
        channelWatcher = new ChannelWatcher();
    }

    public static void start() {
        fileWatcher.start();
        channelWatcher.start();
        while (isRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        fileWatcher.stopWatching();
        channelWatcher.stopWatching();
        isRunning = false;
    }

    private static void initAlert() {
        Map<String, Object> alertConfig = config.getAlertConfig();
        String type = ((String) alertConfig.get("type")).toLowerCase();
        String value = ((String) alertConfig.get("value")).toLowerCase();
        if (type.equals("email")) {
            logAlert = new EmailAlert(value);
        } else if (type.equals("blank")) {
            logAlert = new BlankAlert();
        } else {
            logger.warn("type of {} is not adapted.", type);
        }
    }

    private static void readConfigAndInit() throws IOException {
        ConfigPaser configPaser = new ConfigPaser(CONFIG_FILE_PATH);
        config = configPaser.parseAndInit();
    }

    private static void initFileWatcher() throws IOException {
        String logFilePath = config.getLogFilePath();
        fileWatcher = new FileWatcher(logFilePath);
    }

    private static void initChannel() throws Exception {
        DefaultLogChannel defaultLogChannel = new DefaultLogChannel();
        FilterChain chain = defaultLogChannel.getFilterChain();
        initChain(chain);
        defaultLogChannel.setFilterChain(chain);
        channel = defaultLogChannel;
    }

    private static void initChain(FilterChain chain) throws Exception {
        List<Map<String, Object>> chainConfig = config.getChainConfig();
        for (int i = 0; i < chainConfig.size(); i++) {
            String type = ((String) chainConfig.get(i).get("type")).toLowerCase();
            String value = ((String) chainConfig.get(i).get("value")).toLowerCase();
            if (type.equals("level")) {
                chain.addFilter(new LevelFilter(value));
            } else if (type.equals("keyword")) {
                chain.addFilter(new KeyWordFilter(value));
            } else if (type.equals("regex")) {
                chain.addFilter(new RegexFilter(value));
            } else {
                logger.warn("type of {} is not adapted.", type);
            }
        }
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

    public static ILogAlert getLogAlert() {
        return logAlert;
    }

    public static void setLogAlert(ILogAlert logAlert) {
        Bootstrap.logAlert = logAlert;
    }
}
