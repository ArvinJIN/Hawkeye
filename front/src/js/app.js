var routerApp = angular.module('routerApp', ['ui.router', 'ngGrid', 'AlertConfigPageModule','SearchPageControllerModule','MyFilterModule', 'ServiceModule']);
/**
 * 由于整个应用都会和路由打交道，所以这里把$state和$stateParams这两个对象放到$rootScope上，方便其它地方引用和注入。
 * 这里的run方法只会在angular启动的时候运行一次。
 * @param  {[type]} $rootScope
 * @param  {[type]} $state
 * @param  {[type]} $stateParams
 * @return {[type]}
 */
routerApp.run(function($rootScope, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
});

/**
 * 配置路由。
 * 注意这里采用的是ui-router这个路由，而不是ng原生的路由。
 * ng原生的路由不能支持嵌套视图，所以这里必须使用ui-router。
 * @param  {[type]} $stateProvider
 * @param  {[type]} $urlRouterProvider
 * @return {[type]}
 */
routerApp.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/index');
    $stateProvider
        .state('index', {
            url: '/index',
            views: {
                '': {
                    templateUrl: 'tpls/home.html'
                },
                'main@index': {
                    templateUrl: 'tpls/loginForm.html'
                }
            }
        })
        .state('main', {
            url: '',
            views: { //注意这里的写法，当一个页面上带有多个ui-view的时候如何进行命名和视图模板的加载动作
                '': {
                    templateUrl: 'tpls/main.html'
                },
                'navHead@main': {
                    templateUrl: 'tpls/navHead.html'
                },
                'mainBody@main': {
                    templateUrl: 'tpls/bookGrid.html'
                }
            }
        })
        .state('main.alertConfig', {
            url: '/alert',
            views: {
                'mainBody@main': {
                    templateUrl: 'tpls/alertConfigPage.html'
                }
            }
        })
        .state('main.search', {
            url: '/search',
            views: {
                'mainBody@main': {
                    templateUrl: 'tpls/searchPage.html'
                }
            }
        })
});
