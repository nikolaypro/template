(function () {
    'use strict';

    angular
        .module('app')
        .factory('OrderService', OrderService);

    OrderService.$inject = ['$http', 'UrlService', 'Utils'];
    function OrderService($http, UrlService, Utils) {
        var service = {};
        service.getAll = getAll;
        service.getById = getById;
        service.update = update;
        service.delete = deleteEntity;
        service.getProducts = getProducts;
        service.getMainCloth = getMainCloth;
        service.getCompCloth1 = getCompCloth1;
        service.getCompCloth2 = getCompCloth2;
        return service;

        function getAll(params, handleSuccess) {
            var requestParam = {
                page: params.page(),
                count: params.count(),
                orderBy: params.sorting(),
                filter: params.filter(),
                isOrderAsc: true
            };

            return $http.post(UrlService.url('api/jobs'), requestParam).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getById(id, handleSuccess) {
            return $http.get(UrlService.url('api/jobs/id/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function update(entity, handleSuccess) {
            return $http.post(UrlService.url('api/jobs/update'), entity).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function deleteEntity(ids, handleSuccess) {
            return $http.post(UrlService.url('api/jobs/delete'), ids).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getProducts(handleSuccess) {
            return $http.post(UrlService.url('api/order/products')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getMainCloth(productId, handleSuccess) {
            return $http.post(UrlService.url('api/order/main-clothes'), productId).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getCompCloth1(productId, handleSuccess) {
            return $http.post(UrlService.url('api/order/comp-clothes-1'), productId).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getCompCloth2(productId, handleSuccess) {
            return $http.post(UrlService.url('api/order/comp-clothes-2'), productId).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }
    }

})();
