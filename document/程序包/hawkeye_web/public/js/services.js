//请尝试把BookListCtrl中加载书籍列表数据的部分抽出来作为一个服务
var serviceModule = angular.module('ServiceModule', []);
serviceModule.factory('BaseService', function () {
    var baseUrl = "127.0.0.1:3000";
});