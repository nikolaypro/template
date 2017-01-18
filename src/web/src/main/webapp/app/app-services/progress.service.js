(function () {
    'use strict';

    angular
        .module('app')
        .factory('ProgressService', ProgressService);

    ProgressService.$inject = ['$http', 'UrlService', 'Utils'];
    function ProgressService($http, UrlService, Utils) {
        var service = {};
        service.start = startProgress;
        service.get = getById;
        return service;

        function getById(id, handleSuccess) {
            return $http.get(UrlService.url('api/progress/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function startProgress(handleSuccess) {
            return $http.post(UrlService.url('api/progress/update/start')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

    }

})();
