/**
 * Created by King on 16/4/22.
 */
var db = require('./db');

var $sql = {
    queryAll: 'select * from t_alert_type;'
}

module.exports = {
    queryAll: function (res) {
        db.query($sql.queryAll, [], function (err, result) {
            if(err) {
                console.log('query alert type error.');
                res.json({code: 500, msg: 'query alert type error.'});
            } else {
                res.json({code: 200, msg: result});
            }
        })
    }
}