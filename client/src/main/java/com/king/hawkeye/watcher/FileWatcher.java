package com.king.hawkeye.watcher;

import java.io.*;

/**
 * Created by King on 16/3/10.
 */
public class FileWatcher extends Thread {

    private static final String DIR_PATH = System.getProperty("user.dir") + "/";

    private String fileName;
    private BufferedInputStream bufferedInputStream;

    private ILogChannel channel = Bootstrap.getChannel();

    private boolean isWatching = true;

    private long position;


    public FileWatcher(String fileName) throws IOException {
        this.fileName = fileName;
        File file = new File(DIR_PATH + fileName);
        this.bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        position = file.length();
        System.out.println("??" + file.exists());
        bufferedInputStream.skip(position);
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
                        System.out.println(new String(baos.toByteArray()));
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

    public static void main(String[] args) {
        String fileName = "logs/app.log";
        FileWatcher watcher = null;
        try {
            watcher = new FileWatcher(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        watcher.start();
    }
}
