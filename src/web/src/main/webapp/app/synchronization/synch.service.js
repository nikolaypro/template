(function () {
    'use strict';

    angular
        .module('app')
        .factory('SynchronizationService', SynchronizationService);

    SynchronizationService.$inject = ['$http', 'UrlService', 'Utils'];
    function SynchronizationService($http, UrlService, Utils) {
        var service = {};
        service.synch = synch;
        return service;

        function synch(handler) {
            return $http.post(UrlService.url('api/integration/site-synch')).then(Utils.handleSuccess(handler), handler);
        }

    }

})();
