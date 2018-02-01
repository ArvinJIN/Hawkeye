/**
 * Created by King on 16/4/22.
 */
var db = require('./db');

var $sql = {
    queryAll : 'select * from t_log_filter_type'
}

module.exports = {
    queryAll : function(res) {
        db.query($sql.queryAll, [], function (err, result) {
            if(err) {
                console.log('query all log filter type error.');
                res.json({code: 500, msg: "query filter type error."});
            } else {
                res.json({code: 200, msg: result});
            }
        })
    }
}