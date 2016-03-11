package com.king.hawkeye.watcher;

import com.king.hawkeye.channel.ILogChannel;

import java.io.*;

/**
 * 日志文件 监控器
 * Created by King on 16/3/10.
 */
public class FileWatcher extends Thread {

    private static final String DIR_PATH = System.getProperty("user.dir") + "/";

    private String fileName;
    private BufferedInputStream bufferedInputStream;

    private ILogChannel channel;

    private boolean isWatching = true;

    private long position;


    public FileWatcher(String fileName) throws IOException {
        this.fileName = fileName;
        File file = new File(fileName);
        this.bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        position = file.length();
        bufferedInputStream.skip(position);

        channel = Bootstrap.getChannel();
    }

    @Override
    public void run() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (isWatching) {
            char ch;
            try {
                if (bufferedInputStream.available() > 0) {
                    ch = (char) bufferedInputStream.read();
                    if (ch != '\n') {
                        baos.write(ch);
                    } else {
                        channel.put(new String(baos.toByteArray()));
                        baos.reset();
                    }
                } else {
                    Thread.sleep(200);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopWatching() {
        isWatching = false;
    }
}
