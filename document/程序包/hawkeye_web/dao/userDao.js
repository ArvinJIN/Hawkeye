/**
 * Created by King on 16/4/19.
 */
var db = require("./db");

var $sql = {
    insert:"INSERT INTO hawkeye.t_user(name, group_id, department_id, create_date, account, password)VALUES(?, ?, ?, ?, ?, ?);",
    update:"update t_user set name=? ,group_id=?, department_id=?, password=?",
    delete:"delete from t_user where uid = ?",
    queryById:"select * from t_user where uid = ?",
    queryAll: "select * from t_user",
    queryByAccountAndPwd: "select uid,U.name as name,group_id,G.department_id as department_id,G.name as group_name,D.name as department_name from t_user U left join t_user_group G on U.group_id = G.id left join t_user_department D on G.department_id = D.id  where account = ? and password = ?"
}

module.exports = {
    queryById: function (res, id) {
        db.query($sql.queryById, [id], function (err, rows) {
            res.json(rows);
        });
    },
    queryAll: function (res) {
        db.query($sql.queryAll, [], function (err, rows) {
            res.json(rows);
        });
    },
    insert: function (res, params) {
        var name = params.name;
        var groupId = params.groupId;
        var departmentId = params.departmentId;
        var createDate = new Date();
        var account = params.account;
        var password = params.password;
        if(name === undefined || groupId === undefined || departmentId === undefined || account === undefined || password === undefined) {
            console.log("user info is not complete, cannot insert.");
            res.json({code:400, msg:"信息不全.."});
        } else {
            db.query($sql.insert, [name, groupId, departmentId, createDate, account, password], function (errr, rows) {
                res.json(rows);
            })
        }
    },
    queryByAccountAndPwd: function(account, pwd, callback) {
        db.query($sql.queryByAccountAndPwd, [account, pwd], function(err, rows) {
            callback(rows);
        })
    }
};
