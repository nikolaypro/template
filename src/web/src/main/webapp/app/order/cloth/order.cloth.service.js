(function () {
    'use strict';

    angular
        .module('app')
        .factory('OrderClothService', OrderClothService);

    OrderClothService.$inject = ['$http', 'UrlService', 'Utils'];
    function OrderClothService($http, UrlService, Utils) {
        var service = {};
        service.getById = getById;
        service.update = update;
        service.getClothes = getClothes;
        return service;

        function getById(id, handleSuccess) {
            return $http.get(UrlService.url('api/order-cloth/id/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function update(entity, handleSuccess) {
            return $http.post(UrlService.url('api/order-cloth/update'), entity).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getClothes(handleSuccess) {
            return $http.post(UrlService.url('api/order-cloth/clothes')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

    }

})();
