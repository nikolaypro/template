(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobSubTypeController', JobSubTypeController);

    JobSubTypeController.$inject = ['JobSubTypeService', '$log', 'TableUtils', '$scope'];
    function JobSubTypeController(JobSubTypeService, $log, TableUtils, $scope) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'job-subtype.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job-subtype.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {name: 'asc'};
        params.defaultFilter = {};
        vm.loadJobTypeNames = function(handleSuccess) {
            JobSubTypeService.getJobTypes(function(data) {
                var res = [];
                angular.forEach(data, function(e) {
                    res.push(e.name);
                });
                handleSuccess(res);
            });
        };

        TableUtils.initTablePage(vm, JobSubTypeService, $scope, params);
    }
})();

