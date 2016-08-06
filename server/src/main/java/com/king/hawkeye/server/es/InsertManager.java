package com.king.hawkeye.server.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;

import java.util.*;

/**
 * Created by King on 16/4/12.
 */
public class InsertManager {

    private static final Log LOG = LogFactory.getLog(InsertManager.class);

    public static Map save(String index, String type, String idName, JSONObject json) {
        List<JSONObject> list = new ArrayList<JSONObject>();
        list.add(json);
        return save(index, type, idName, list);
    }

    public static Map save(String index, String type, String idName, List<JSONObject> listData) {
        BulkRequestBuilder bulkRequestBuilder = ESTools.client.prepareBulk().setRefresh(true);
        Map resultMap = new HashMap();

        for (Object object : listData) {
            JSONObject json = (JSONObject) JSON.toJSON(object);
            if(idName == null || idName.length() <= 0) {
                IndexRequestBuilder indexRequestBuilder = ESTools.client.prepareIndex(index, type).setSource(json);
                bulkRequestBuilder.add(indexRequestBuilder);
            } else {
                String idValue = json.getString(idName);
                IndexRequestBuilder indexRequestBuilder = ESTools.client.prepareIndex(index, type, idValue).setSource(json);
            }
        }

        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            LOG.info(bulkResponse.getItems().toString());
            resultMap.put(500, "保存失败!");
        } else {
            resultMap.put(200, "保存成功!");
        }
        return resultMap;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", new Date());
        jsonObject.put("log", "Exception log...");
        InsertManager.save("log_index", "logMsg", "_id", jsonObject);
    }
}
