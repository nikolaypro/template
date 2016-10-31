(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobSubTypeCostController', JobSubTypeController);

    JobSubTypeCostController.$inject = ['JobSubTypeCostService', '$log', 'TableUtils', '$scope'];
    function JobSubTypeController(JobSubTypeCostService, $log, TableUtils, $scope) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'job-subtype-cost.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job-subtype-cost.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {name: 'asc'};
        TableUtils.initTablePage(vm, JobSubTypeCostService, $scope, params);
    }
})();

