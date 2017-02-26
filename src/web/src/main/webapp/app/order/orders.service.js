(function () {
    'use strict';

    angular
        .module('app')
        .factory('OrdersService', OrdersService);

    OrdersService.$inject = ['$http', 'UrlService', 'Utils'];
    function OrdersService($http, UrlService, Utils) {
        var service = {};
        service.getAll = getAll;
        return service;

        function getAll(params, handleSuccess) {
            var requestParam = {
                page: params.page(),
                count: params.count(),
                orderBy: params.sorting(),
                filter: params.filter(),
                isOrderAsc: true
            };
            return $http.post(UrlService.url('api/orders'), requestParam).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }
    }

})();
