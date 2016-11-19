(function () {
    'use strict';

    angular
        .module('app-report', ['ngRoute', 'localization', 'mascotCommon'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider'];
    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'no-selected-report.html',
                controllerAs: 'vm'
            })
            .when('/users/:unique', {
                controller: 'UsersShowReportController',
                templateUrl: 'users/users-show-report.view.html',
                controllerAs: 'vm'
            })
            .when('/salary/:unique', {
                controller: 'SalaryShowReportController',
                templateUrl: 'salary/salary-show-report.view.html',
                controllerAs: 'vm'
            })
            .when('/salary-log/:unique', {
                controller: 'SalaryShowLogReportController',
                templateUrl: 'salary/salary-show-log-report.view.html',
                controllerAs: 'vm'
            })
            .otherwise({ redirectTo: '/' });

    }

    run.$inject = ['$rootScope'];
    function run($rootScope) {
        $rootScope.globals = {currentUser: user};
    }

})();