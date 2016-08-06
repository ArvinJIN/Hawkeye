/**
 * Created by King on 16/4/22.
 */
var db = require('./db');

var $sql = {
    queryByProjectId: 'SELECT C.id,C.project_id, P.name as project_name, C.config FROM t_alert_config C left join t_project P on C.project_id = P.id where project_id = ?',
    insert: 'INSERT INTO t_alert_config(project_id,config) VALUES (?,?)',
    deleteConfigById: 'delete from t_alert_config where id = ?'
}

module.exports = {
    queryByProjectId: function (res, projectId) {
        console.log("projectId : " + projectId);
        db.query($sql.queryByProjectId, [projectId], function (err, result) {
            if (err) {
                console.log('query alert config error. project id is ' + projectId);
                res.json({code: 500, msg: 'query alert config error.'});
            } else {
                // result.config = JSON.parse(result.config);
                res.json({code: 200, msg: result});
            }
        })
    },
    insert: function (res, project_id, config) {
        db.query($sql.insert, [project_id, config], function (err, result) {
            if(err) {
                console.log('insert alert config error. project_id = ' + project_id + ', config is ' + config + "\n" + err);
                res.json({code: 500, msg: 'insert alert config error.'});
            } else {
                res.json({code: 200, msg: result});
            }
        })
    },
    deleteConfigById: function (res, configId) {
        db.query($sql.deleteConfigById, [configId], function(err, result) {
            if(err) {
                console.log('delete alert config error. project_id = ' + project_id + ', config is ' + config + "\n" + err);
                res.json({code: 500, msg: 'insert alert config error.'});
            } else {
                console.log('delete alert config success: ' + result);
                res.json({code: 200, msg: result});
            }
        })
    }
}