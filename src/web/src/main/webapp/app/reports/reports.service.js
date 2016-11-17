(function () {
    'use strict';

    angular
        .module('app')
        .factory('ReportsService', ReportsService);

    ReportsService.$inject = ['$http', 'UrlService', '$rootScope'];
    function ReportsService($http, UrlService, $rootScope) {
        var service = {};

        service.openReport = openReport;
        service.reportUsers = reportUsers;
        service.reportUsersData = reportUsersData;
        service.reportSalaryData = reportSalaryData;
        return service;

        function openReport(url, name, data) {
            var win = window.open('app/app-report/report.html#/' + url + '/' + new Date().getTime(), name);
            win.user = $rootScope.globals.currentUser;
            win.data = data;

        }

        function reportUsers(handleSuccess) {
            return $http.post(UrlService.url('api/reports/users'), {responseType: 'arraybuffer'}).then(handleSuccess, handleError);
        }

        function reportUsersData(handleSuccess) {
            return $http.post(UrlService.url('api/reports/users-data')).then(handleSuccess, handleError);
        }

        function reportSalaryData(date, handleSuccess) {
            return $http.post(UrlService.url('api/reports/salary-data'), date).then(handleSuccess, handleError);
        }

        function handleError(response) {
//            FlashService.Error(response.data.error);
            return function () {
                return { success: false, message: response.data.error};
            };
        }

    }

})();
