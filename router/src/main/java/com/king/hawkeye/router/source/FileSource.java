package com.king.hawkeye.router.source;

import com.king.hawkeye.router.channel.AbstractRouterChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * 日志文件 监控器
 * Created by King on 16/3/10.
 */
public class FileSource extends AbstractSource {

    private Logger logger = LogManager.getLogger(FileSource.class);

    private static final String DIR_PATH = System.getProperty("user.dir") + "/";

    private String fileName;

    private BufferedInputStream bufferedInputStream;

    private ByteArrayOutputStream baos;

    private AbstractRouterChannel channel;

    private long position = 0L;


    public FileSource(String fileName, AbstractRouterChannel channel) throws IOException {
        super(channel);
        this.fileName = fileName;
        File file = new File(fileName);
        this.bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        this.baos  = new ByteArrayOutputStream();
        position = file.length();
        bufferedInputStream.skip(position);

        this.channel = channel;
    }

    @Override
    public void source() {
        while (isRunning) {
            char ch;
            try {
                if (bufferedInputStream.available() > 0) {
                    ch = (char) bufferedInputStream.read();
                    if (ch != '\n') {
                        baos.write(ch);
                    } else {
                        String info = new String(baos.toByteArray());
                        logger.info("log info: " + info);
                        channel.put(info);
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
}
