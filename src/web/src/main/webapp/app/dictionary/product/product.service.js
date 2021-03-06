(function () {
    'use strict';

    angular
        .module('app')
        .factory('ProductService', ProductService);

    ProductService.$inject = ['$http', 'UrlService', 'Utils'];
    function ProductService($http, UrlService, Utils) {
        var service = {};
        service.getAll = getAll;
        service.getById = getById;
        service.update = update;
        service.delete = deleteEntity;
        service.getForFilter = getForFilter;
        return service;

        function getAll(params, handleSuccess) {
            var requestParam = {
                page: params.page(),
                count: params.count(),
                orderBy: params.sorting(),
                isOrderAsc: true
            };

            return $http.post(UrlService.url('api/products'), requestParam).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getById(id, handleSuccess) {
            return $http.get(UrlService.url('api/products/id/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function update(user, handleSuccess) {
            return $http.post(UrlService.url('api/products/update'), user).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function deleteEntity(ids, handleSuccess) {
            return $http.post(UrlService.url('api/products/delete'), ids).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getForFilter(handleSuccess) {
            return $http.post(UrlService.url('api/products/all')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

    }

})();
