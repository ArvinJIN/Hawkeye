var alertConfigPageModule = angular.module("AlertConfigPageModule", []);
alertConfigPageModule.controller('AlertConfigCtrl', function ($scope) {
    $scope.configs = [
        {
            "id": 1,
            "project_id": 1,
            "config": {
                "name": "101异常警报",
                "filter": [{"type": "level", "value": "error"}, {"type": "keyword", "value": "error-101"}],
                "alert": {"type": "email", "value": "123@qq.com", "template": "出现101异常!请及时排查."}
            }
        },
        {
            "id": 1,
            "project_id": 1,
            "config": {
                "name": "异常Exception",
                "filter": [{"type": "level", "value": "error"}, {"type": "keyword", "value": "Exception"}],
                "alert": {
                    "type": "blank",
                    "value": "blank_value",
                    "template": "里的互动是指，人与物体之间的互动，即物体能够根据人的行为而做出反应。这章通篇都在开脑洞。。。直到最后一段的总结表达了作者的观点，下面是最后一段：互动的程度在提升，并且将继续提升。然而简单的非互动的事物仍将存在，比如木柄锤子。尽管如此，互动化的社会中越来越有价值的的将是包括智能锤子在内的那些可以互动的事物。可是，高互动性也会带来一定的成本。想要互动，就需要掌握技能、学会配合、多加体验，并加强学习。我们需要把自己嵌入到技术中，并培养自身能力。之所以如此，更因为我们才刚刚发明出互动的新方法。未来的技术发展很大程度上将取决于新型互动方式的发掘。在未来的30年里，任何无法实现密切互动的事物都将被当作“坏”掉的东西。"
                }
            }
        }
    ];

    $scope.removeConfig = function (id) {
        if (confirm("确定要删除这条配置吗?")) {
            console.log("remove config : " + id);
        }
    }
});

alertConfigPageModule.controller('AlertConfigModalCtrl', function ($scope, $http, BaseService) {
    $scope.filters = [{"type": "level", "value": "error"}, {"type": "keyword", "value": "error-101"}];
    $scope.editFilter = {"type": "level", "value": "error"};
    $scope.alert = {"type": "email", "value": "123@qq.com", "template": "出现101异常!请及时排查."};
    $scope.name = "101异常警报";

    $scope.addFilter = function (e) {
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

            $scope.filters[$scope.filters.length] = {"type": $scope.editFilter.type, "value": $scope.editFilter.value};
            $scope.editFilter = {"type": "level", "value": ""};
        }
    };


    $scope.saveAlertConfig = function () {
        var url = "localhost:3000/alerts/config/add";
        var data = {
            projectId: $scope.projectId,
            config: {
                name: $scope.name,
                filter: $scope.filters,
                alert: $scope.alert
            }
        };
        transFn = function (data) {
            return $.param(data);
        };
        postCfg = {
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            transformRequest: transFn
        };

        $http.post(url, data, {'Content-Type':'application/x-www-form-urlencoded'}).success(function () {
            alert("保存成功!");
        })
    }
});

var searchPageControllerModule = angular.module("SearchPageControllerModule", []);
searchPageControllerModule.controller('SearchPageCtrl', function($scope){
    
})

/**
 * 这里是书籍列表模块
 * @type {[type]}
 */
var bookListModule = angular.module("BookListModule", []);
bookListModule.controller('BookListCtrl', function ($scope, $http, $state, $stateParams) {
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [5, 10, 20],
        pageSize: 5,
        currentPage: 1
    };
    $scope.setPagingData = function (data, page, pageSize) {
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.books = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    //这里可以根据路由上传递过来的bookType参数加载不同的数据
    console.log($stateParams);
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var data;
            if (searchText) {
                var ft = searchText.toLowerCase();
                $http.get('../src/data/books' + $stateParams.bookType + '.json')
                    .success(function (largeLoad) {
                        data = largeLoad.filter(function (item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data, page, pageSize);
                    });
            } else {
                $http.get('../src/data/books' + $stateParams.bookType + '.json')
                    .success(function (largeLoad) {
                        $scope.setPagingData(largeLoad, page, pageSize);
                    });
            }
        }, 100);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.gridOptions = {
        data: 'books',
        rowTemplate: '<div style="height: 100%"><div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell ">' +
        '<div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }"> </div>' +
        '<div ng-cell></div>' +
        '</div></div>',
        multiSelect: false,
        enableCellSelection: true,
        enableRowSelection: false,
        enableCellEdit: true,
        enablePinning: true,
        columnDefs: [{
            field: 'index',
            displayName: '序号',
            width: 60,
            pinnable: false,
            sortable: false
        }, {
            field: 'name',
            displayName: '书名',
            enableCellEdit: true
        }, {
            field: 'author',
            displayName: '作者',
            enableCellEdit: true,
            width: 220
        }, {
            field: 'pubTime',
            displayName: '出版日期',
            enableCellEdit: true,
            width: 120
        }, {
            field: 'price',
            displayName: '定价',
            enableCellEdit: true,
            width: 120,
            cellFilter: 'currency:"￥"'
        }, {
            field: 'bookId',
            displayName: '操作',
            enableCellEdit: false,
            sortable: false,
            pinnable: false,
            cellTemplate: '<div><a ui-sref="bookdetail({bookId:row.getProperty(col.field)})" id="{{row.getProperty(col.field)}}">详情</a></div>'
        }],
        enablePaging: true,
        showFooter: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions
    };
});


/**
 * 这里是书籍详情模块
 * @type {[type]}
 */
var bookDetailModule = angular.module("BookDetailModule", []);
bookDetailModule.controller('BookDetailCtrl', function ($scope, $http, $state, $stateParams) {
    console.log($stateParams);
    //请模仿上面的代码，用$http到后台获取数据，把这里的例子实现完整

});
