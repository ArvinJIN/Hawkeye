var express = require('express');
var router = express.Router();
var es = require('../dao/es');

/* GET home page. */
router.get('/:projectName/:keyword', function(req, res, next) {
    console.log('enter..');
    var projectName = req.params.projectName;
    var keyword = req.params.keyword;
    var result = es.search(projectName, keyword, function(resp) {
        console.log("result: " + resp);
        res.json({ code: 200, msg: resp });
    });
});

module.exports = router;
