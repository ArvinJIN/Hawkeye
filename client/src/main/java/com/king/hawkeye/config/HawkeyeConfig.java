package com.king.hawkeye.config;

import java.util.List;
import java.util.Map;

/**
 * Hawkeye 配置
 * Created by King on 16/3/10.
 */
public class HawkeyeConfig {

    private String logFilePath;

    private List<Map<String, Object>> chainConfig;

    private Map<String, Object> alertConfig;

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public List<Map<String, Object>> getChainConfig() {
        return chainConfig;
    }

    public void setChainConfig(List<Map<String, Object>> chainConfig) {
        this.chainConfig = chainConfig;
    }

    public Map<String, Object> getAlertConfig() {
        return alertConfig;
    }

    public void setAlertConfig(Map<String, Object> alertConfig) {
        this.alertConfig = alertConfig;
    }
}
