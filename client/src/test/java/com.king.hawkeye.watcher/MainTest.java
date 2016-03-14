package com.king.hawkeye.watcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

/**
 * Created by King on 16/3/11.
 */
public class MainTest {
    private static final Logger logger = LogManager.getLogger(MainTest.class);
    @Test
    public void testInit() throws Exception {
        Bootstrap.init();
    }

    @Test
    public void testStart() throws IOException, InterruptedException {
        try {
            Bootstrap.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bootstrap.start();
    }

    @Test
    public void log() throws InterruptedException, IOException {
        final boolean running = true;
        new Runnable(){
            @Override
            public void run() {
                while(true){
                    long currentTime = System.currentTimeMillis();
                    if(currentTime % 16 == 0){
                        logger.info("log for keyword test : keyword is King. ");
                    } else if (currentTime % 48 == 1){
                        logger.debug("log for level test : level is debug.");
                    } else if (currentTime % 16 == 1){
                        logger.error("error info.");
                    } else if (currentTime % 17 == 2) {
                        logger.error("error and keyword king.", new Exception("test exception..."));
                    } else if (currentTime% 77 == 1) {
                        logger.error("Exception come out..");
                    } else {
                        logger.info("...");
                    }
                    try {
                        Thread.sleep(1000 + new Random().nextInt(2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.run();
    }
}
