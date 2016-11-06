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
        service.checkNotExists = checkNotExists;
        service.updateCostOnly = updateCostOnly;
        service.delete = deleteEntity;
        service.getJobSubTypes = getJobSubTypes;
        service.getProducts = getProducts;
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

        function update(entity, handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype-cost/update'), entity).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function checkNotExists(entity, handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype-cost/not-exists'), entity).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function updateCostOnly(entity, handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype-cost/update-cost'), entity).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
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
