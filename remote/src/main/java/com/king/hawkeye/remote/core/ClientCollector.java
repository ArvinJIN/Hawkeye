package com.king.hawkeye.remote.core;

import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by King on 16/4/13.
 */
public class ClientCollector {
    private static final Logger LOG = LogManager.getLogger(ClientCollector.class);

    private static Map<String, Integer> projectMap = new HashMap<String, Integer>();

    private static Map<String, String> hostProjectMap = new HashMap<String, String>();

    private static Map<String, String> hostClientMap = new HashMap<String, String>();

    private static Map<String, Channel> clientChannelMap = new HashMap<String, Channel>();

    public static void regist(String hostAndPort, String projectName, Channel channel) {
        LOG.info("client regist : " + hostAndPort + " " + projectName);
        String clientName;
        int count;
        if (projectMap.containsKey(projectName)) {
             count = projectMap.get(projectName) + 1;
        } else {
            count = 1;
        }

        clientName = projectName + "-" + count;
        projectMap.put(projectName, count);
        hostProjectMap.put(hostAndPort, projectName);
        hostClientMap.put(hostAndPort, clientName);
        clientChannelMap.put(clientName, channel);
    }

    public static void remove(String hostAndPort) {
        String projectName = hostProjectMap.get(hostAndPort);
        String clientName = hostClientMap.get(hostAndPort);

        LOG.info("client remove : " + hostAndPort + " " + projectName + " " + clientName);

        projectMap.put(projectName, projectMap.get(projectName) - 1);
        hostProjectMap.remove(hostAndPort);
        hostClientMap.remove(hostAndPort);
        clientChannelMap.remove(clientName);
    }

    public static boolean isRegisted(String hostAndPort) {
        return hostClientMap.containsKey(hostAndPort);
    }

    public static String getClientName(String hostAndPort) {
        return hostClientMap.get(hostAndPort);
    }

    public static String getProjectName(String hostAndPort) {
        return hostProjectMap.get(hostAndPort);
    }

    public static Channel getChannel(String hostAndPort) {
        return clientChannelMap.get(hostAndPort);
    }

}
