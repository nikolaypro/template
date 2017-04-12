(function () {
    'use strict';

    angular
        .module('app')
        .factory('SynchronizationService', SynchronizationService);

    SynchronizationService.$inject = ['$http', 'UrlService', 'Utils'];
    function SynchronizationService($http, UrlService, Utils) {
        var service = {};
        service.synch = synch;
        service.getLog = getLog;
        return service;

        function synch(progressId, handler) {
            return $http.post(UrlService.url('api/integration/site-synch'), progressId).then(Utils.handleSuccess(handler), handler);
        }

        function getLog(date, handler) {
            return $http.post(UrlService.url('api/integration/log'), date).then(Utils.handleSuccess(handler), handler);
        }

    }

})();
