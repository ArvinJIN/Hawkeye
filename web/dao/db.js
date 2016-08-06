/**
 * Created by King on 16/4/20.
 */
var mysql = require('mysql');
var $conf = require('../conf/dbConf');

var pool = mysql.createPool($conf.mysql);

module.exports = {
    query: function(sql, param, callback) {
        pool.getConnection(function(err, connection) {
            console.log('sql : ' + sql + ", param = " + param);
            if (err) {
                console.log(err);
            }
            connection.query(sql, param, callback);
            connection.release();
        });
    }
};
