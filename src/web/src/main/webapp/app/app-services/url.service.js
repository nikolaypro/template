(function () {
    'use strict';
    angular
        .module('app')
        .factory('UrlService', UrlService);

    UrlService.$inject = ['$location'];
    function UrlService($location) {
        var longUrls = ['/logout', '/reports/'];

        var service = {};
        service.url = getApiUrl;
        service.isApiUrl = isApiUrl;
        service.isShowLongRequest = isShowLongRequest;
        return service;

        function getApiUrl(url) {
            return $location.protocol() + '://' + $location.host() + ':' + $location.port() + '/template/' + url;
        }
        function isApiUrl(url) {
            return typeof url != 'undefined' &&  url.indexOf('/template/') > -1;
        }
        function isShowLongRequest(url) {
            for (var i = 0; i < longUrls.length; i++) {
                if (url.indexOf(longUrls[i]) > -1) {
                    return true;
                }
            }
            return false;
        }
    }
})();