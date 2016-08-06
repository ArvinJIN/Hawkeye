package com.king.hawkeye.client.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * 配置文件 解析器
 * Created by King on 16/3/10.
 */
public class ConfigPaser {

    private Logger logger = LogManager.getLogger(ConfigPaser.class);

    private JSONObject totalConfig;

    public ConfigPaser(String path) throws IOException {
        String configStr = "";
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String str;
        while ((str = reader.readLine()) != null) {
            configStr += str;
        }
        totalConfig = JSON.parseObject(configStr);
        logger.debug("totalConfig: " + totalConfig.toString());
    }

    /**
     * parse the configuration in configFile
     *
     * @return Hawkeye configuration.
     * @throws IOException the config in configFile in not complete.
     */
    public HawkeyeConfig parseAndInit() throws IOException {
        if (!isContainKeyAndNotNull(totalConfig, "project")) {
            throw new IOException("缺少project");
        }
        if (!isContainKeyAndNotNull(totalConfig, "file_path")) {
            throw new IOException("缺少file_path.");
        }
        if (!isContainKeyAndNotNull(totalConfig, "chain")) {
            throw new IOException("totalConfig has no chain.");
        }
        if (!isContainKeyAndNotNull(totalConfig, "alert")) {
            throw new IOException("缺少alert.");
        }
        String project = parseProjectName(totalConfig.get("project"));
        String logFilePath = parseFilePath(totalConfig.get("file_path"));
        List<Map<String, Object>> chainConfig = parseChain(totalConfig.get("chain"));
        Map<String, Object> alertConfig = parseAlert(totalConfig.get("alert"));

        HawkeyeConfig config = new HawkeyeConfig();
        config.setProject(project);
        config.setLogFilePath(logFilePath);
        config.setChainConfig(chainConfig);
        config.setAlertConfig(alertConfig);
        return config;
    }

    /**
     * parse project name
     *
     * @param config : project value in totalConfig
     * @return log project name
     * @throws IOException type of input is not String
     */
    private String parseProjectName(Object config) throws IOException {
        if (!(config instanceof String)) {
            throw new IOException("project config is not a String.");
        }
        return (String) config;
    }

    /**
     * parse log file path
     *
     * @param config : file_path value in totalConfig
     * @return log file path
     * @throws IOException type of input is not String
     */
    private String parseFilePath(Object config) throws IOException {
        if (!(config instanceof String)) {
            throw new IOException("file_path config is not a String.");
        }
        return (String) config;
    }

    /**
     * parse alert config
     *
     * @param config : alert value in totalConfig
     * @return map of filter config
     */
    private Map<String, Object> parseAlert(Object config) throws IOException {
        if(!(config instanceof JSONObject)){
            throw new IOException("alert config is not a JSONObject.");
        }
        return (JSONObject) config;
    }

    /**
     * parse chain of filters
     *
     * @param config : chain value in totalConfig
     * @return list of filters config map
     * @throws IOException input is not a JSONObject or input has lost some thing config.
     */
    private List<Map<String, Object>> parseChain(Object config) throws IOException {
        if (!(config instanceof JSONArray)) {
            throw new IOException("chain config is not a JSONArray.");
        }
        List<Map<String, Object>> chainConfig = new ArrayList<>();
        JSONArray filters = (JSONArray) config;
        for (int i = 0; i < filters.size(); i++) {
            JSONObject filter = (JSONObject) filters.get(i);
            if (!isContainKeyAndNotNull(filter, "type") || !isContainKeyAndNotNull(filter, "value")) {
                throw new IOException("filter config is not complete.");
            }

            Map<String, Object> filterConfig = parseFilter(filter);
            chainConfig.add(filterConfig);
        }
        return chainConfig;
    }

    /**
     * parse filter config
     *
     * @param config : item in chain config array.
     * @return map of filter config
     */
    private Map<String, Object> parseFilter(JSONObject config) {
        return config;
    }



    private boolean isContainKeyAndNotNull(JSONObject obj, String key) {
        if (!obj.containsKey(key))
            return false;
        return obj.get(key) != null && !obj.get(key).equals("");
    }
}
