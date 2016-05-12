/*
(function () {
    'use strict';
    angular
        .module('app')
        .factory('RestService', RestService);

    UserService.$inject = ['$http', 'UrlService', 'FlashService'];
    function UserService($http, UrlService, FlashService) {
        var service = {};

        service.post = post;

        return service;

        function post(requestParam, handleSuccess, handleError) {
            return $http.post(UrlService.url('api/users'), requestParam).then(handleSuccess, handleError('Error getting all users'));
//            return $http.get(UrlService.url('api/users')).then(handleSuccess, handleError('Error getting all users'));
        }

        function onSuccess(data) {
            if ()
            return data.data;
        }

        function onError(error) {
            FlashService.Error(error);
        }

    }
})();*/
