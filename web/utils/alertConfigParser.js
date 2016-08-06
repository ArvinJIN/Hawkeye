/**
 * Created by King on 16/4/22.
 */

var str = {"id":1,"project_id":1,"project_name":"hawkeye","config":"{\n  \"file_path\": \"/Users/King/Work/code58/hawkeye/client/logs/app.log\",\n  \"chain\": [\n    {\n      \"type\": \"level\",\n      \"value\": \"error\"\n    },{\n      \"type\": \"keyword\",\n      \"value\": \"Exception\"\n    }\n  ],\n  \"alert\": {\n    \"type\": \"blank\",\n    \"value\": \"jinleihao@58ganji.com\"\n  }\n}"};

var filter = {
    levelFilter: function (log) {
        
    }
}

var parser = {

}

module.exports = parser;

