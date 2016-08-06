package com.king.hawkeye.server.alert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.hawkeye.remote.core.RemoteInfo;
import com.king.hawkeye.router.channel.AbstractRouterChannel;
import com.king.hawkeye.router.filter.*;
import com.king.hawkeye.server.channel.RemoteInfoChannel;
import com.king.hawkeye.server.dao.AlertConfigDao;
import com.king.hawkeye.server.dao.IAlertConfigDao;
import com.king.hawkeye.server.entity.AlertConfig;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by King on 16/4/23.
 */
public class AlertClient extends Thread {
    private static final Logger LOG = LogManager.getLogger(AlertClient.class);

    private AbstractRouterChannel<RemoteInfo> alertMsgChannel = new RemoteInfoChannel();

    private Map<String, Integer[]> projectIds = new HashMap<String, Integer[]>();
    private Map<Integer, AbstractFilterChain<RemoteInfo>> filterChainMap = new HashMap<Integer, AbstractFilterChain<RemoteInfo>>();
    private Map<Integer, IAlert> alertMap = new HashMap<Integer, IAlert>();

    private boolean isRunning = false;

    private IAlertConfigDao alertConfigDao = new AlertConfigDao();

    public void init() {
        List<AlertConfig> configs = alertConfigDao.getAll();
        for (AlertConfig config : configs) {
            try {
                parseConfig(config.getId(), config.getConfig());
            } catch (Exception e) {
                LOG.error("alert config has error, config id is " + config.getId() + ", config content is " + config.getConfig());
            }
        }

    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            LOG.debug("alert msg channel size : " + alertMsgChannel.getLogQueue().size());
            RemoteInfo info = alertMsgChannel.take();

            String projectName = info.getProjectName();
            Integer[] configIds;
            if (projectIds.containsKey(projectName)) {
                configIds = projectIds.get(projectName);
            } else {
                configIds = alertConfigDao.getIdsByProjectName(projectName);
                projectIds.put(projectName, configIds);
            }
            LOG.debug("config ids is " + ArrayUtils.toString(configIds));
//            int[] configIds = new int[]{1};
            for (int configId : configIds) {
                if (!filterChainMap.containsKey(configId) || !alertMap.containsKey(configId)) {
                    LOG.info("filter map or alert map don't have the config id : " + configId);
                }
                RemoteInfo filterResult = filterChainMap.get(configId).filter(info);
                if (filterResult != null) {
                    try {
                        alertMap.get(configId).alert((String) info.getInfo());
                    } catch (EmailException e) {
                        LOG.error("alert error. alert type is " + alertMap.get(configId).getClass().getSimpleName() + ", message is " + info.getInfo());
                    }
                }
            }
        }
    }

    private void parseConfig(int configId, String config) {
        JSONObject obj = JSON.parseObject(config);
        JSONObject filterAndAlert = (JSONObject) obj;
        AbstractFilterChain<RemoteInfo> filterChain = parseFilter(filterAndAlert.getJSONArray("filter"));
        IAlert alert = parseAlert(filterAndAlert.getJSONObject("alert"));
        filterChainMap.put(configId, filterChain);
        alertMap.put(configId, alert);
    }

    private IAlert parseAlert(JSONObject alertConfig) {
        IAlert alert = null;
        String alertType = alertConfig.getString("type");
        String alertTarget = alertConfig.getString("target");
        String template = alertConfig.getString("template");

        if (alertType.toLowerCase().equals("email")) {
            LOG.debug("create email alert..");
            alert = new EmailAlert(alertTarget);
        } else if (alertType.toLowerCase().equals("blank")) {
            LOG.debug("create blank alert..");
            alert = new BlankAlert();
        } else {
            LOG.error("alert type is wrong: " + alertType);
        }

        return alert;
    }

    private AbstractFilterChain<RemoteInfo> parseFilter(JSONArray filters) {
        LOG.debug("creating filter chain..");
        AbstractFilterChain<RemoteInfo> chain = new AbstractFilterChain<RemoteInfo>() {
            @Override
            public String getFilterContent(RemoteInfo info) {
                return (String) info.getInfo();
            }
        };

        for (Object obj : filters) {
            JSONObject filter = (JSONObject) obj;
            String filterType = filter.getString("type");
            String filterValue = filter.getString("value");

            if (filterType.toLowerCase().equals("level")) {
                try {
                    LOG.debug("add level filter..");
                    chain.addFilter(new LevelFilter(filterValue));
                } catch (Exception e) {
                    LOG.error("level of the filter config is wrong.");
                }
            } else if (filterType.toLowerCase().equals("regex")) {
                LOG.debug("add regex filter..");
                chain.addFilter(new RegexFilter(filterValue));
            } else if (filterType.toLowerCase().equals("blank")) {
                LOG.debug("add blank filter..");
                chain.addFilter(new BlankFilter());
            } else if (filterType.toLowerCase().contentEquals("keyword")) {
                LOG.debug("add keyword filter..");
                try {
                    chain.addFilter(new KeyWordFilter(filterValue));
                } catch (Exception e) {
                    LOG.error("keyword of th filter config is wrong: " + filterValue);
                }
            } else {
                LOG.error("filter type is wrong: " + filterType);
            }
        }
        return chain;
    }

    public AbstractRouterChannel<RemoteInfo> getAlertMsgChannel() {
        return alertMsgChannel;
    }

//    public void setAlertMsgChannel(AbstractRouterChannel<RemoteInfo> alertMsgChannel) {
//        this.alertMsgChannel = alertMsgChannel;
//    }
}
