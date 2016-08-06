/**
 * Created by King on 16/4/22.
 */
var db = require("./db");

var $sql = {
    queryByGroupId : 'SELECT P.*,G.name as group_name,D.name as department_name FROM t_project P left join t_user_group G on P.group_id = G.id left join t_user_department D on P.department_id = D.id where group_id = ?'
}

module.exports = {
    queryByGroupId : function (groupId, callback) {
        db.query($sql.queryByGroupId, [groupId], function (err, result) {
            callback(err, result);
        })
    }
}