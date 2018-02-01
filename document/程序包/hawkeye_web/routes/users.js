var express = require('express');
var userDao = require('../dao/userDao');
var projectDao = require('../dao/projectDao');
var router = express.Router();

/* GET users listing. */
router
    .get('/query/:id', function(req, res) {
        console.log('userId = ' + req.params.id);
        userDao.queryById(res, req.params.id);
    })
    .get('/query_all', function(req, res) {
        userDao.queryAll(res);
    })
    .get('/insert', function(req, res) {
        console.log('user info : ');
        for (var param in req.query) {
            console.log(param + ":" + req.query[param]);
        }
        userDao.insert(res, req.query);
    })
    .post('/login', function(req, res) {
        var account = req.body.account;
        var password = req.body.password;

        userDao.queryByAccountAndPwd(account, password, function(result) {
            var user = result;
            if (user != undefined && user.length != 0) {
                console.log("login success..");
                console.log(user.length);
                console.log(user);
                res.json({ code: 200, msg: user[0] });
            } else {
                console.log("login false..");
                res.json({ code: 500, msg: "登录失败！" });
            }
        });
    })
    .get("/projects/:groupId", function(req, res) {
        var groupId = req.params.groupId;
        projectDao.queryByGroupId(groupId, function(err, result){
            if (err) {
                console.log('query project by group id erorr. groupId is ' + groupId);
                res.json({code: 500, msg: 'query project error.'});
            } else {
                res.json({code: 200, msg: result});
            }
        })
    })

module.exports = router;
