/**
 * Created by King on 16/4/22.
 */
var express = require('express');
var alertConfigDao = require('../dao/alertConfigDao');
var userDao = require('../dao/userDao');
var projectDao = require('../dao/projectDao');
var logFilterTypeDao = require('../dao/logFilterTypeDao');
var alertTypeDao = require('../dao/alertTypeDao');
var router = express.Router();

router
    .get('/config/query/:projectId', function (req, res) {
        console.log('userId = ' + req.params.projectId);
        alertConfigDao.queryByProjectId(res, req.params.projectId);
    })
    .get('/filter/type/all', function (req, res) {
        logFilterTypeDao.queryAll(res);
    })
    .get('/alert/type/all', function (req, res) {
        alertTypeDao.queryAll(res);
    })
    .post('/config/add', function (req, res) {
        console.log("config: " + req.body.config.name);
        alertConfigDao.insert(res, req.body.projectId, JSON.stringify(req.body.config));
    })
    .delete('/config/delete/:configId', function(req, res) {
        console.log("configId: " + req.params.configId);
        alertConfigDao.deleteConfigById(res, req.params.configId);
    })

module.exports = router;