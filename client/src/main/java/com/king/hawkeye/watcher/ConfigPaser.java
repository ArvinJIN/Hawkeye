package com.king.hawkeye.watcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
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
        System.out.println("totalConfig: " + totalConfig.toString());
    }

    public void parseAndInit() throws IOException {
        if (!isContainKeyAndNotNull(totalConfig, "file_path")) {
            throw new IOException("缺少file_path.");
        }
        if (!isContainKeyAndNotNull(totalConfig, "chain")) {
            throw new IOException("totalConfig has no chain.");
        }
        if (!isContainKeyAndNotNull(totalConfig, "alert")) {
            throw new IOException("缺少alert.");
        }

        parseFilePath(totalConfig.get("file_path"));
        parseChain(totalConfig.get("chain"));
    }

    private void parseFilePath(Object config) throws IOException {
        if (!(config instanceof String)) {
            throw new IOException("file_path config is not a String.");
        }
        System.out.println(config);
    }

    private void parseChain(Object config) throws IOException {
        if (!(config instanceof JSONArray)) {
            throw new IOException("chain config is not a JSONArray.");
        }

        JSONArray filters = (JSONArray) config;
        for (int i = 0; i < filters.size(); i++) {
            JSONObject filter = (JSONObject) filters.get(i);
            if (!isContainKeyAndNotNull(filter, "type") || !isContainKeyAndNotNull(filter, "value")) {
                throw new IOException("filter config is not complete.");
            }
            String filterType = filter.getString("type");
            String filterValue = filter.getString("value");
            System.out.println("filter: " + filterType + "--" +filterValue);

            parseFilter(filterType, filterValue);
        }
    }

    private void parseFilter(String type, String value) throws IOException {
        if(type.toLowerCase().equals("level")){

        } else if (type.toLowerCase().equals("keyword")){

        } else if (type.toLowerCase().equals("regex")){

        } else {
            throw new IOException("there is not filter type of " + type);
        }
    }


    private boolean isContainKeyAndNotNull(JSONObject obj, String key) {
        if (!obj.containsKey(key))
            return false;
        return obj.get(key) != null && !obj.get(key).equals("");
    }
}
