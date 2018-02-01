var controllerModule = angular.module("ControllerModule", []);
controllerModule.controller('navHeadController', function($scope, $rootScope) {

    if ($rootScope.userId === undefined || $rootScope.userId === '') {
        console.log("not login...");
        $rootScope.$state.go('index');
    }

    $scope.changeProject = function(project) {
        $rootScope.nowProject = project;
        console.log($rootScope.nowProject);
        console.log("reload.." + $rootScope.$state.current.name);
        $rootScope.$state.go($rootScope.$state.current.name, {}, {reload: true});
    }
})

controllerModule.controller('AlertConfigCtrl', function($scope, $rootScope, $http) {
    $scope.configs = [];

    $http.get("/alerts/config/query/" + $rootScope.nowProject.id)
        .success(function(result) {
            if (result.code === 200) {

                //JSON字符串转成JSON Object
                for (var i = 0; i < result.msg.length; i++) {
                    result.msg[i].config = JSON.parse(result.msg[i].config);
                }

                $scope.configs = result.msg;
                // $scope.$apply();
            } else {
                console.log("server error.");
            }
        });

    $scope.removeConfig = function(id) {
        if (confirm("确定要删除这条配置吗?")) {
            alert("remove config : " + id);
            $http.delete('/alerts/config/delete/' + id)
                .success(function(result) {
                    if (result.code === 200) {
                        alert("删除成功！");
                        for (var i = 0; i < $scope.configs.length; i++) {
                            if ($scope.configs[i].id === id) {
                                $scope.configs.removeItem(i);

                            }
                        }
                    } else {
                        alert("删除失败！");
                    }
                });
        }
    }
});



controllerModule.controller('AlertConfigModalCtrl', function($scope, $rootScope, $http, BaseService) {

    $scope.filters = [];
    $scope.editFilter = {};
    $scope.alert = {};
    $scope.name = "";

    $scope.addFilter = function(e) {
        var keycode = window.event ? e.keyCode : e.which;
        if (keycode == 13) {
            if ($scope.editFilter.value === "") {
                alert("过滤器参数不能为空!");
                return false;
            }
            if ($scope.editFilter.type === 'level' || $scope.editFilter.type === 'regex') {
                for (filter in $scope.filters) {
                    if ($scope.filters[filter].type === 'level' || $scope.filters[filter].type === 'regex') {
                        alert("只能添加一个" + ($scope.filters[filter].type === 'level' ? "日志级别过滤器!" : "正则匹配过滤器!"));
                        return false;
                    }
                }
            }

            $scope.filters[$scope.filters.length] = { "type": $scope.editFilter.type, "value": $scope.editFilter.value };
            $scope.editFilter = { "type": "level", "value": "" };
        }
    };

    $scope.delFilter = function(filter) {
        var index = findSameFilter(filter, $scope.filters);
        $scope.filters.removeItem(index);
    }

    function findSameFilter(filter, filters) {
        for (i in filters) {
            if (filter.type === filters[i].type && filter.value === filters[i].value) {
                return i;
            }
        }
        return -1;
    }


    $scope.saveAlertConfig = function() {
        //for test
        $scope.projectId = 1;

        var url = "/alerts/config/add";
        var data = {
            projectId: $scope.projectId,
            config: {
                name: $scope.name,
                filter: $scope.filters,
                alert: $scope.alert
            }
        };

        // $http.post(url, data, {}).success(function () {
        //     alert("保存成功!");
        // })

        $.post(url, data, function() {
            alert("保存成功!");
        })
    }
});

controllerModule.controller('SearchPageCtrl', function($scope, $rootScope, $location) {
    $scope.logs = [];
    $scope.page = 0;
    $scope.allResults = false;
    $scope.searchTerm = '';

    $scope.search = function() {
        $scope.page = 0;
        $scope.logs = [];
        $scope.allResults = false;
        // $location.search({ 'q': $scope.searchTerm });
        $scope.loadMore();
    };

    $scope.loadMore = function() {
        var url = "/search/" + $rootScope.nowProject.name + "/" + $scope.searchTerm;
        $.get(url, function(results) {
            // if (results.length !== 10) {
            //     $scope.allResults = true;
            // }

            // var ii = 0;
            // for (; ii < results.length; ii++) {
            //     $scope.logs.push(results[ii]);
            // }
            if (results.code == 200) {
                $scope.logs = results.msg.hits.hits;
                console.log($scope.logs.length);
                $scope.$apply();
            }
        })

    };

    $scope.loadMore();
});

controllerModule.controller("loginController", function($scope, $rootScope) {

    $scope.login = function() {
        var url = "/users/login";
        var data = {
            account: $scope.account,
            password: $scope.password
        };

        $.post(url, data, function(result) {
            if (result.code == 200) {
                $rootScope.groupId = result.msg.group_id;
                $rootScope.userId = result.msg.uid;
                $rootScope.username = result.msg.name;
                alert("登录成功！");
                console.log("$rootScope.groupId = " + $rootScope.groupId);

                var url = "/users/projects/" + $rootScope.groupId;
                $.get(url, function(result) {
                    if (result.code == 200) {
                        $rootScope.projects = result.msg;
                        $rootScope.nowProject = result.msg[0];
                        console.log("$rootScope.nowProject = " + $rootScope.nowProject);
                        // $scope.$apply();
                    }
                })
                $rootScope.$state.go('main.alertConfig');
            } else {
                alert("登录失败！");
            }
        });
    }
});
