package com.king.hawkeye.demo;

import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by King on 16/3/10.
 */
public class DirectoryWatcherTest {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("LogInfo");

    private static final String DIR_PATH = System.getProperty("user.dir");
    private static final File DIR = new File(DIR_PATH);
    private static final String SUFFIX = ".txt";
    private static final String PREFIX = "test";
    private static final int ADD_TIMES = 3;

    public class Logger implements Observer {
        @Override
        public void update(Observable observable, Object eventArgs) {
            FileSystemEventArgs args = (FileSystemEventArgs) eventArgs;
            System.out.printf("%s has been %s\n", args.getFileName(), args.getKind());
            assertTrue(args.getFileName().startsWith(PREFIX));
            assertEquals(ENTRY_CREATE, args.getKind());
        }
    }

    @Test
    public void testWatchFile() throws IOException, InterruptedException {
        DirectoryWatcher watcher = new DirectoryWatcher(DIR_PATH);
        Logger l1 = new Logger();
        watcher.addObserver(l1);
        watcher.execute();

        //    延迟等待后台任务的执行
        Thread.sleep(100000000);
        watcher.shutdown();
        System.out.println("finished");
    }

    @Test
    public void test() throws IOException {
        boolean running = true;
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(DIR_PATH + "/log.txt"));

        while (running) {
            if (reader.available() > 0) {
                char c = (char) reader.read();
                System.out.print(c == '\n' ? "\\n" : c);
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    running = false;
                }
            }
        }
    }
}
