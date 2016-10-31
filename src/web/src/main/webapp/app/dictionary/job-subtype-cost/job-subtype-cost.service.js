(function () {
    'use strict';

    angular
        .module('app')
        .factory('JobSubTypeCostService', JobSubTypeCostService);

    JobSubTypeCostService.$inject = ['$http', 'UrlService', 'Utils'];
    function JobSubTypeCostService($http, UrlService, Utils) {
        var service = {};
        service.getAll = getAll;
        service.getById = getById;
        service.update = update;
        service.delete = deleteEntity;
        service.getJobTypes = getJobTypes;
        return service;

        function getAll(params, handleSuccess) {
            var requestParam = {
                page: params.page(),
                count: params.count(),
                orderBy: params.sorting(),
                isOrderAsc: true
            };

            return $http.post(UrlService.url('api/job-subtype-cost'), requestParam).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getById(id, handleSuccess) {
            return $http.get(UrlService.url('api/job-subtype-cost/id/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function update(user, handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype-cost/update'), user).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function deleteEntity(ids, handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype-cost/delete'), ids).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getJobSubTypes(handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype-cost/job-subtypes')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getProducts(handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype-cost/products')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }
    }

})();
