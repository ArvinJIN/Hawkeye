package com.king.hawkeye.server.source;

import com.king.hawkeye.remote.core.NettyServer;
import com.king.hawkeye.remote.core.ProcessHandler;
import com.king.hawkeye.remote.core.RemoteInfo;
import com.king.hawkeye.router.channel.AbstractRouterChannel;
import com.king.hawkeye.router.source.AbstractSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/4/16.
 */
public class NettySource extends AbstractSource {

    private static final Logger LOG = LogManager.getLogger(NettySource.class);

    private NettyServer nettyServer;

    public NettySource(final AbstractRouterChannel<RemoteInfo> channel, int serverPort) throws InterruptedException {
        super(channel);
        nettyServer = new NettyServer(serverPort, new ProcessHandler() {
            public void process(RemoteInfo remoteInfo) {
                if(remoteInfo == null){
                    return;
                }
                channel.put(remoteInfo);
            }
        });
        nettyServer.start();
    }

    public void source() {
        while (isRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.error("bootstrap : thread sleep interrupted..");
            }
        }
        LOG.warn("netty source thread is stoped !");
    }
}
