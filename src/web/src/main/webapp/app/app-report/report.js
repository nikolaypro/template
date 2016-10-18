(function () {
    'use strict';

    angular
        .module('app-report', ['ngRoute', 'localization'])
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
            .otherwise({ redirectTo: '/' });

    }

    run.$inject = ['$rootScope'];
    function run($rootScope) {
        $rootScope.globals = {currentUser: user};
    }

})();