var myFilter = angular.module('MyFilterModule', []);

myFilter.filter("FilterTypeFilter", function () {
    return function (input) {
        if (input === 'level') {
            return '日志级别';
        } else if (input === 'regex') {
            return '正则匹配';
        } else if (input === 'keyword') {
            return '关键词';
        } else {
            return '其他类别';
        }
    }
});

myFilter.filter('AlertTypeFilter', function () {
    return function (input) {
        if (input === 'blank') {
            return '空白';
        } else if (input === 'email') {
            return '邮件';
        } else {
            return '其他类别';
        }
    }
});

myFilter.filter('AlertTemplateFilter', function () {
    return function (input) {
        if (input.length > 50) {
            input = input.substring(0,50) + "...";
        }
        return input;
    }
})
