package com.king.hawkeye.client.sink;

import com.king.hawkeye.remote.core.NettyClient;
import com.king.hawkeye.router.channel.IRouterChannel;
import com.king.hawkeye.router.sink.AbstractSink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/4/11.
 */
public class UploadSink extends AbstractSink<String> {
    private static final Logger LOG = LogManager.getLogger(UploadSink.class);
    private NettyClient nettyClient;

    public UploadSink(IRouterChannel channel, String serverHost, int serverPort) throws Exception {
        super(channel);
        nettyClient  = new NettyClient("meizhou-api");
        nettyClient.connect(serverPort, serverHost);
    }

    @Override
    public void sink(String message) {
        nettyClient.send(message);
    }
}
