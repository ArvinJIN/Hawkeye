package com.king.hawkeye.client.boot;

import com.king.hawkeye.client.sink.UploadSink;
import com.king.hawkeye.client.config.ConfigPaser;
import com.king.hawkeye.client.config.HawkeyeConfig;
import com.king.hawkeye.router.Router;
import com.king.hawkeye.router.channel.AbstractRouterChannel;
import com.king.hawkeye.router.channel.DefaultRouterChannel;
import com.king.hawkeye.router.filter.AbstractFilterChain;
import com.king.hawkeye.router.filter.KeyWordFilter;
import com.king.hawkeye.router.filter.LevelFilter;
import com.king.hawkeye.router.filter.RegexFilter;
import com.king.hawkeye.router.sink.AbstractSink;
import com.king.hawkeye.router.sink.BlankSink;
import com.king.hawkeye.router.sink.EmailSink;
import com.king.hawkeye.router.sink.SinkChain;
import com.king.hawkeye.router.source.AbstractSource;
import com.king.hawkeye.router.source.FileSource;
import com.king.hawkeye.router.source.TestSource;
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

    private static Router<String> nettyRouter;

    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/config/config.json";
    private static boolean isRunning = true;

    private Bootstrap() {}

    public static void main(String[] args) {
        try {
            System.out.println("Hawkeye client init...");
            Bootstrap.init();
        } catch (Exception e) {
            logger.error("Hawkeye client init error.", e);
            return;
        }
        System.out.println("Hawkeye client start...");
        Bootstrap.start();
    }

    public static void init() throws Exception {
        readConfigAndInit();

//        initChannel();
        DefaultRouterChannel channel = new DefaultRouterChannel();
        AbstractFilterChain<String> chain = channel.getFilterChain();
        initChain(chain);
        channel.setAbstractFilterChain(chain);

//        initSource();
        AbstractSource source = null;
        String logFilePath = config.getLogFilePath();
        source = new FileSource(logFilePath, channel);
        // TODO: 16/4/23  测试用
//        source = new TestSource(channel, 3000, "[ERROR] Exception: second this is test log !!!!!");

//        initSink();
        AbstractSink<String> sink = null;
        Map<String, Object> alertConfig = config.getAlertConfig();
        String type = ((String) alertConfig.get("type")).toLowerCase();
        String value = ((String) alertConfig.get("value")).toLowerCase();
        if (type.equals("email")) {
            sink = new EmailSink(value);
        } else if (type.equals("blank")) {
            sink = new BlankSink();
        } else if (type.equals("upload")) {
            String[] hostAndPort = value.split(":");
            sink = new UploadSink(config.getProject(), channel, hostAndPort[0], Integer.valueOf(hostAndPort[1]));
        } else {
            logger.warn("type of {} is not adapted.", type);
        }

        nettyRouter = new Router<String>(source, channel, sink);
    }

    public static void start() {
        nettyRouter.start();
        while (isRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        nettyRouter.stop();
        isRunning = false;
    }

    private static void readConfigAndInit() throws IOException {
        ConfigPaser configPaser = new ConfigPaser(CONFIG_FILE_PATH);
        config = configPaser.parseAndInit();
    }

    private static void initChain(AbstractFilterChain<String> chain) throws Exception {
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

}
