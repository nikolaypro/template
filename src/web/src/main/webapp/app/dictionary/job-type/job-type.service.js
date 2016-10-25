(function () {
    'use strict';

    angular
        .module('app')
        .factory('JobTypeService', JobTypeService);

    JobTypeService.$inject = ['$http', 'UrlService', 'Utils'];
    function JobTypeService($http, UrlService, Utils) {
        var service = {};
        service.getAll = getAll;
        service.getById = getById;
        service.update = update;
        service.delete = deleteEntity;
        service.moveDown = moveDown;
        service.moveUp = moveUp;
        return service;

        function getAll(params, handleSuccess) {
            var requestParam = {
                page: params.page(),
                count: params.count(),
                orderBy: params.sorting(),
                isOrderAsc: true
            };

            return $http.post(UrlService.url('api/job-type'), requestParam).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function getById(id, handleSuccess) {
            return $http.get(UrlService.url('api/job-type/id/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function update(user, handleSuccess) {
            return $http.post(UrlService.url('api/job-type/update'), user).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function deleteEntity(ids, handleSuccess) {
            return $http.post(UrlService.url('api/job-type/delete'), ids).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function moveDown(id, handleSuccess) {
            return $http.post(UrlService.url('api/job-type/move_down/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }

        function moveUp(id, handleSuccess) {
            return $http.post(UrlService.url('api/job-type/move_up/' + id)).then(Utils.handleSuccess(handleSuccess), Utils.handleError);
        }
    }

})();
