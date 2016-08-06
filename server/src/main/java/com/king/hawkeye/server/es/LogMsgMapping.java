package com.king.hawkeye.server.es;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by King on 16/4/12.
 */
public class LogMsgMapping {
    private static final Log LOG = LogFactory.getLog(LogMsgMapping.class);

    public static XContentBuilder getMapping()
    {
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("_ttl").field("enabled", false).endObject()
                        .startObject("properties")
                            .startObject("date").field("type", "date").endObject()
                            .startObject("log").field("type", "string").endObject()
                        .endObject()
                    .endObject();
        } catch (IOException e) {
            LOG.error("get log message mapping error.", e);
        }
        return mapping;
    }

    public static void createMapping() {
        PutMappingRequest mapping = Requests.putMappingRequest("log_index").type("logMsg").source(getMapping());
        ESTools.client.admin().indices().putMapping(mapping).actionGet();
    }

    public static void main(String[] args) {
//        CreateIndexRequest request = new CreateIndexRequest("log_index");
//        ESTools.client.admin().indices().create(request);
        LogMsgMapping.createMapping();
    }
}
