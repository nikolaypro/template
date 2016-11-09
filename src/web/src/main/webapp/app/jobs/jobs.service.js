(function () {
    'use strict';

    angular
        .module('app')
        .factory('JobService', JobService);

    JobService.$inject = ['$http', 'UrlService', 'Utils'];
    function JobService($http, UrlService, Utils) {
        var service = {};
        service.getAll = getAll;
        service.getById = getById;
        service.update = update;
        service.delete = deleteEntity;
        service.getJobTypes = getJobTypes;
        service.getProducts = getProducts;
        return service;

        function getAll(params, handleSuccess) {
            var requestParam = {
                page: params.page(),
                count: params.count(),
                orderBy: params.sorting(),
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

        function getJobTypes(handleSuccess) {
            return $http.post(UrlService.url('api/jobs/job-types')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getProducts(handleSuccess) {
            return $http.post(UrlService.url('api/jobs/products')).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }
    }

})();
