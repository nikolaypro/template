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
        TableUtils.initTablePage(vm, JobSubTypeService, $scope, params);
        var individuals = ["a1","b1","c1"];
    }
})();

