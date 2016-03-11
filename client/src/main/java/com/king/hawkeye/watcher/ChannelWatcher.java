package com.king.hawkeye.watcher;

import com.king.hawkeye.alert.ILogAlert;
import com.king.hawkeye.channel.ILogChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志信息管道 监控器
 * Created by King on 16/3/11.
 */
public class ChannelWatcher extends Thread {
    private static final Logger logger = LogManager.getLogger(ChannelWatcher.class);
    private ILogChannel channel = Bootstrap.getChannel();
    private ILogAlert alert = Bootstrap.getLogAlert();
    private boolean isWatching = true;

    @Override
    public void run() {
        while(isWatching){
            String logInfo = channel.take();
//            System.out.println("channel watched : " + logInfo);
            alert.alert("logInfo come : " + logInfo);
        }
    }

    public void stopWatching(){
        this.isWatching = false;
    }
}
