package com.king.hawkeye.alert;

import com.king.hawkeye.server.core.NettyClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by King on 16/4/11.
 */
public class UploadAlert implements ILogAlert {
    private static final Log LOG = LogFactory.getLog(UploadAlert.class);
    private NettyClient nettyClient;

    public UploadAlert(String serverHost, int serverPort) throws Exception {
        nettyClient  = new NettyClient();
        nettyClient.connect(serverPort, serverHost);
    }

    @Override
    public void alert(String message) {
        LOG.info("message upload : " + message);
        nettyClient.send(message);
    }
}
