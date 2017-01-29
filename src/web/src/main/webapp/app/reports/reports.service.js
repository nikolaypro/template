(function () {
    'use strict';

    angular
        .module('app')
        .factory('ReportsService', ReportsService);

    ReportsService.$inject = ['$http', 'UrlService', '$rootScope', 'Utils', 'LocMsg', '$filter'];
    function ReportsService($http, UrlService, $rootScope, Utils, LocMsg, $filter) {
        var service = {};

        service.openReport = openReport;
        service.reportUsers = reportUsers;
        service.reportUsersData = reportUsersData;
        service.reportSalaryData = reportSalaryData;
        service.reportSalaryWithSubTypesData = reportSalaryWithSubTypesData;
        service.reportSalaryWithGroupData = reportSalaryWithGroupData;
        service.loadLogFileList = loadLogFileList;
        service.loadLogFile = loadLogFile;
        return service;

        function openReport(url, name, data) {
            var win = window.open('app/app-report/report.html#/' + url + '/' + new Date().getTime(), name + "_" + new Date().getTime());
            if (win == undefined) {
                Utils.showWarning(LocMsg.get('report.please.enable.popup.window'));
                return;
            }
            win.user = $rootScope.globals.currentUser;
            win.data = data;
            win.data.creationDate = $filter('date')(new Date(), 'yyyy-MM-dd hh:mm:ss');
            setTimeout(function() {
                setTitle(win, name + ", created:  " + win.data.creationDate);
            }, 1000);
        }

        function setTitle(win, title) {
            if(win.document) { // if loaded
                win.document.title = title; // set title
            } else { // if not loaded yet
                setTimeout(function() {
                    setTitle(title);
                }, 100);
            }
        }

        function reportUsers(handleSuccess) {
            return $http.post(UrlService.url('api/reports/users'), {responseType: 'arraybuffer'}).then(handleSuccess, handleError);
        }

        function reportUsersData(handleSuccess) {
            return $http.post(UrlService.url('api/reports/users-data')).then(handleSuccess, handleError);
        }

        function reportSalaryData(date, progressId, handleSuccess) {
            return $http.post(UrlService.url('api/reports/salary-data'), {date: date, progressId: progressId}).then(handleSuccess, handleError);
        }

        function reportSalaryWithSubTypesData(date, progressId, handleSuccess) {
            return $http.post(UrlService.url('api/reports/salary-data-subtype'), {date: date, progressId: progressId}).then(handleSuccess, handleError);
        }

        function loadLogFileList(handleSuccess) {
            return $http.post(UrlService.url('api/administration/salary-logs')).then(handleSuccess, handleError);
        }

        function loadLogFile(fileName, handleSuccess) {
            return $http.post(UrlService.url('api/administration/salary-load-log'), fileName).then(handleSuccess, handleError);
        }

        function reportSalaryWithGroupData(date, progressId, handleSuccess) {
            return $http.post(UrlService.url('api/reports/salary-grouped'), {date: date, progressId: progressId}).then(handleSuccess, handleError);
        }

        function handleError(response) {
//            FlashService.Error(response.data.error);
            return function () {
                return { success: false, message: response.data.error};
            };
        }

    }

})();
