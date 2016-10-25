(function () {
    'use strict';

    angular
        .module('app')
        .factory('JobSubTypeService', JobSubTypeService);

    JobSubTypeService.$inject = ['$http', 'UrlService', 'Utils'];
    function JobSubTypeService($http, UrlService, Utils) {
        var service = {};
        service.getAll = getAll;
        service.getById = getById;
        service.update = update;
        service.delete = deleteEntity;
        return service;

        function getAll(params, handleSuccess) {
            var requestParam = {
                page: params.page(),
                count: params.count(),
                orderBy: params.sorting(),
                isOrderAsc: true
            };

            return $http.post(UrlService.url('api/job-subtype'), requestParam).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getById(id, handleSuccess) {
            return $http.get(UrlService.url('api/job-subtype/id/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function update(user, handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype/update'), user).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function deleteEntity(ids, handleSuccess) {
            return $http.post(UrlService.url('api/job-subtype/delete'), ids).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }
    }

})();
