(function () {
    'use strict';
    angular
        .module('app')
        .factory('UrlService', UrlService);

    UrlService.$inject = ['$location'];
    function UrlService($location) {
        var service = {};
        service.url = getApiUrl;
        service.isApiUrl = isApiUrl;
        return service;

        function getApiUrl(url) {
            return $location.protocol() + '://' + $location.host() + ':' + $location.port() + '/template/' + url;
        }
        function isApiUrl(url) {
            return typeof url != 'undefined' &&  url.indexOf('/template/') > -1;
        }
    }
})();