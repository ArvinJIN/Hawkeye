//package com.king.hawkeye.router.sink;
//
//import com.king.hawkeye.remote.core.NettyClient;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
///**
// * Created by King on 16/4/11.
// */
//public class UploadSink extends AbstractSink {
//    private static final Logger LOG = LogManager.getLogger(UploadSink.class);
//    private NettyClient nettyClient;
//
//    public UploadSink(String serverHost, int serverPort) throws Exception {
//        nettyClient  = new NettyClient("meizhou-api");
//        nettyClient.connect(serverPort, serverHost);
//    }
//
//    @Override
//    public void sink(String message) {
//        nettyClient.send(message);
//    }
//}
