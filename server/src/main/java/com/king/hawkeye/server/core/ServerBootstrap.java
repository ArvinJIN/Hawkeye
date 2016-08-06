package com.king.hawkeye.server.core;

import com.king.hawkeye.remote.core.RemoteInfo;
import com.king.hawkeye.router.Router;
import com.king.hawkeye.router.channel.AbstractRouterChannel;
import com.king.hawkeye.router.sink.AbstractSink;
import com.king.hawkeye.router.sink.ChannelSink;
import com.king.hawkeye.router.sink.SinkChain;
import com.king.hawkeye.server.alert.AlertClient;
import com.king.hawkeye.server.sink.ESSink;
import com.king.hawkeye.server.channel.RemoteInfoChannel;
import com.king.hawkeye.server.source.NettySource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/4/14.
 */
public class ServerBootstrap extends Thread {
    private static final Logger LOG = LogManager.getLogger(ServerBootstrap.class);

    private static final int SERVER_PORT = 8927;

    private AlertClient alertClient;

    private Router nettyRouter;

//    private NettySource nettySource;

//    private AbstractRouterChannel<RemoteInfo> remoteInfoIChannel;

//    private AbstractSink<RemoteInfo> chainSink;

    private boolean isRunning = true;

    public ServerBootstrap() throws Exception {

        LOG.debug("init alertClient init..");
        this.alertClient = new AlertClient();
        this.alertClient.init();

        LOG.debug("remote info channel init..");
        RemoteInfoChannel remoteInfoIChannel = new RemoteInfoChannel();
        NettySource nettySource = new NettySource(remoteInfoIChannel, SERVER_PORT);

        LOG.debug("init es sink..");
        AbstractSink<RemoteInfo> esSink = new ESSink();
        LOG.debug("init channel sink..");
        AbstractSink<RemoteInfo> channelSink = new ChannelSink<RemoteInfo>(alertClient.getAlertMsgChannel());

        this.nettyRouter = new Router(nettySource, remoteInfoIChannel, esSink,channelSink);
    }

    @Override
    public void run() {
        try {
            nettyRouter.start();
            alertClient.start();
        } catch (Exception e) {
            LOG.error("server bootstrap start fail ! ");
            isRunning = false;
        }
        while (isRunning) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                LOG.error("bootstrap : thread sleep interrupted..");
                break;
            }
        }
        LOG.info("server bootstrap stopped.");
    }

    public static void main(String[] args) {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.start();
        } catch (Exception e) {
            LOG.error("bootstrap init error: ", e);
        }
    }
}
