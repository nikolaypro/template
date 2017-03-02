(function () {
    'use strict';

    angular
        .module('app')
        .factory('SiteService', SiteService);

    SiteService.$inject = ['$http', 'UrlService', 'Utils'];
    function SiteService($http, UrlService, Utils) {
        var service = {};
        service.synch = synch;
        return service;

        function synch(handleSuccess) {
            return $http.post(UrlService.url('api/integration/site-synch')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }


    }

})();
