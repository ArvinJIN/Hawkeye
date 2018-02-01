var elasticsearch = require('elasticsearch');

// Connect to localhost:9200 and use the default settings
var es = new elasticsearch.Client();

module.exports = {
    search: function(projectName, keyword, callback) {
        console.log("start search..");
        es.search({
            index: 'log_index',
            type: 'logMsg',
            size: 50,
            body: {
                "query": { "bool": { "must": [{ "term": { "projectName": projectName } }, { "term": { "info": keyword } }], "must_not": [], "should": [] } },
                "from": 0,
                "size": 10,
                "sort": [],
                "aggs": {}
            }
        }).then(function(resp) {
            callback(resp);
        });
    }
}
