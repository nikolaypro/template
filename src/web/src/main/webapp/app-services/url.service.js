(function () {
    'use strict';
    angular
        .module('app')
        .factory('UrlService', UrlService);

    UrlService.$inject = ['$location'];
    function UrlService($location) {
        var service = {};
        service.url = getApiUrl;
        return service;

        function getApiUrl(url) {
            return $location.protocol() + '://' + $location.host() + ':' + $location.port() + '/template/' + url;
        }
    }
})();