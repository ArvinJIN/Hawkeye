package com.king.hawkeye.watcher;

import com.king.hawkeye.client.boot.Bootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
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
        File file = new File("/Users/King/app.log");
        final FileWriter fw = new FileWriter(file);
        new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long currentTime = System.currentTimeMillis();
                    try {
                        if (currentTime % 5 == 1) {
                            fw.write("Log error info : target Exception come out ..\n");
                            fw.flush();
                        } else if (currentTime % 5 == 2) {
                            fw.write("Log error info : target Important come out..\n");
                            fw.flush();
                        } else {
                            fw.write("Log error info : this is a normal log..\n");
                            fw.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(1000 + new Random().nextInt(10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.run();
    }
}
