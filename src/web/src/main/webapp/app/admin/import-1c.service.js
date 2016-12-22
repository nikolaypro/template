(function () {
    'use strict';

    angular
        .module('app')
        .factory('Import1cService', Import1cService);

    Import1cService.$inject = ['$http', 'UrlService', 'Utils'];
    function Import1cService($http, UrlService, Utils) {
        var service = {};
        service.checkImport = checkImport;
        service.doImport = doImport;
        service.getProgress = getProgress;
        return service;

        function checkImport(handleSuccess) {
            return $http.post(UrlService.url('api/import-1c/check-import')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function doImport(handleSuccess) {
            return $http.post(UrlService.url('api/import-1c/do-import')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getProgress(handleSuccess) {
            return $http.post(UrlService.url('api/import-1c/progress')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }
    }

})();
