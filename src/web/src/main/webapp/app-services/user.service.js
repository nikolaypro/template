(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http', 'UrlService', 'FlashService'];
    function UserService($http, UrlService, FlashService) {
        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll(params, handleSuccess) {
            var requestParam = {
                page: params.page(),
                count: params.count(),
                orderBy: params.sorting(),
                isOrderAsc: true
            };

            return $http.post(UrlService.url('api/users'), requestParam).then(handleSuccess, handleError);
//            return $http.get(UrlService.url('api/users')).then(handleSuccess, handleError('Error getting all users'));
        }

        function GetById(id) {
            return $http.get('/api/users/' + id).then(handleSuccess, handleError);
        }

        function GetByUsername(username) {
            return $http.get(UrlService.url('api/users/' + username)).then(handleSuccess, handleError);
        }

        function Create(user) {
            return $http.post('/api/users', user).then(handleSuccess, handleError);
        }

        function Update(user) {
            return $http.put('/api/users/' + user.id, user).then(handleSuccess, handleError);
        }

        function Delete(id) {
            return $http.delete('/api/users/' + id).then(handleSuccess, handleError);
        }

        // private functions

        function handleSuccess(data) {
            return data.data;
        }

        function handleError(response) {
//            FlashService.Error(response.data.error);
            return function () {
                return { success: false, message: response.data.error};
            };
        }
    }

})();
