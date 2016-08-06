package com.king.hawkeye.server.es;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Created by King on 16/4/12.
 */
public class ESTools {
    private static final Log LOG = LogFactory.getLog(ESTools.class);

    public final static Client client = build();

    public final static Class clz = ESTools.class;

    private static Client build() {
        if(null != client) {
            return client;
        }

        Properties properties = null;
        try {
            properties = new Properties();
            InputStream in = new FileInputStream(new File(System.getProperty("user.dir") + "/config/es.properties"));
            properties.load(in);
        } catch (IOException e) {
            LOG.error("Load es.properties error.", e);
        }

        String ip = properties.getProperty("host");
        String port = properties.getProperty("port");
        String clusterName = properties.getProperty("cluster.name");
        String clientTransportSniff = properties.getProperty("client.transport.sniff");

        Client client = null;

        Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).put("client.transport.sniff", clientTransportSniff).build();
        try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), Integer.parseInt(port)));
        } catch (UnknownHostException e) {
            LOG.error("ESTools build error.", e);
        }
        return client;
    }
}
