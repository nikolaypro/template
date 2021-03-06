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
            .when('/salary-with-subtype/:unique', {
                controller: 'SalaryWithSubtypeShowReportController',
                templateUrl: 'salary-with-subtype/salary-with-subtype-show-report.view.html',
                controllerAs: 'vm'
            })
            .when('/salary-with-group/:unique', {
                controller: 'SalaryWithGroupShowReportController',
                templateUrl: 'salary-with-group/salary-with-group-show-report.view.html',
                controllerAs: 'vm'
            })
            .when('/salary-investigation/:unique', {
                controller: 'SalaryInvestigationShowReportController',
                templateUrl: 'salary-investigation/salary-investigation-show-report.view.html',
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