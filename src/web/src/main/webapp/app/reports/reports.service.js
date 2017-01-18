(function () {
    'use strict';

    angular
        .module('app')
        .factory('ReportsService', ReportsService);

    ReportsService.$inject = ['$http', 'UrlService', '$rootScope', 'Utils', 'LocMsg'];
    function ReportsService($http, UrlService, $rootScope, Utils, LocMsg) {
        var service = {};

        service.openReport = openReport;
        service.reportUsers = reportUsers;
        service.reportUsersData = reportUsersData;
        service.reportSalaryData = reportSalaryData;
        service.loadLogFileList = loadLogFileList;
        service.loadLogFile = loadLogFile;
        return service;

        function openReport(url, name, data) {
            var win = window.open('app/app-report/report.html#/' + url + '/' + new Date().getTime(), name);
            if (win == undefined) {
                Utils.showWarning(LocMsg.get('report.please.enable.popup.window'))
                return;
            }
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
            return $http.post(UrlService.url('api/reports/salary-data'), {date: date, id: 1}).then(handleSuccess, handleError);
        }

        function loadLogFileList(handleSuccess) {
            return $http.post(UrlService.url('api/administration/salary-logs')).then(handleSuccess, handleError);
        }

        function loadLogFile(fileName, handleSuccess) {
            return $http.post(UrlService.url('api/administration/salary-load-log'), fileName).then(handleSuccess, handleError);
        }


        function handleError(response) {
//            FlashService.Error(response.data.error);
            return function () {
                return { success: false, message: response.data.error};
            };
        }

    }

})();
