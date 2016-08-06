package com.king.hawkeye.server.sink;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.king.hawkeye.remote.core.RemoteInfo;
import com.king.hawkeye.router.channel.AbstractRouterChannel;
import com.king.hawkeye.router.sink.AbstractSink;
import com.king.hawkeye.server.es.InsertManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/4/14.
 */
public class ESSink extends AbstractSink<RemoteInfo> {
    private static final Logger LOG = LogManager.getLogger(ESSink.class);

    private String indexName = "log_index";
    private String type = "logMsg";

    public ESSink(){}

    public ESSink(String index, String type) {
        this.indexName = index;
        this.type = type;
    }

    public void sink(RemoteInfo info) {
        InsertManager.save(indexName, type, null, toJSON(info));
    }

    private JSONObject toJSON(RemoteInfo info) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(info);
        System.out.println("json object : " + jsonObject.toJSONString());
        return jsonObject;
    }

}
