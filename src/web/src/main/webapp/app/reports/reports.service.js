(function () {
    'use strict';

    angular
        .module('app')
        .factory('ReportsService', ReportsService);

    ReportsService.$inject = ['$http', 'UrlService'];
    function ReportsService($http, UrlService) {
        var service = {};

        service.reportUsers = reportUsers;
        return service;

        function reportUsers(handleSuccess) {
            return $http.post(UrlService.url('api/reports/users'), {responseType: 'arraybuffer'}).then(handleSuccess, handleError);
        }

        function handleError(response) {
//            FlashService.Error(response.data.error);
            return function () {
                return { success: false, message: response.data.error};
            };
        }

    }

})();
