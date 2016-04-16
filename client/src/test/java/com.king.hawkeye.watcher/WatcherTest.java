//package com.king.hawkeye.watcher;
//
//import com.king.hawkeye.client.boot.Bootstrap;
//import com.king.hawkeye.router.source.FileSource;
//import org.apache.logging.log4j.LogManager;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//
///**
// * Created by King on 16/3/9.
// */
//public class WatcherTest {
//    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("WatcherTest");
//    private static final String DIR_PATH = System.getProperty("user.dir");
//
//
//
//    @Test
//    public void watcher() throws IOException, InterruptedException {
//        //生成log信息数量
//        int logCount = 100;
//
//        //开启日志文件监控
//        FileSource watcher = new FileSource("logs/app.log");
//        watcher.start();
//
//        //在日志文件中插入日志信息
//        log("test log", logCount);
//        Thread.sleep(2000);
//
//        //检查queue中的log条数与生成的log条数是否一致
//        assertEquals(logCount, Bootstrap.getChannel());
//    }
//
//    private void log(final String log, final int count) throws InterruptedException, IOException {
//        final boolean running = true;
//        new Runnable(){
//            @Override
//            public void run() {
//                for(int i = 0; i < count; i ++){
//                    logger.info(log + i);
//                }
//            }
//        }.run();
//    }
//}
