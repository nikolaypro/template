(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobSubTypeCostController', JobSubTypeCostController);

    JobSubTypeCostController.$inject = ['JobSubTypeCostService', '$log', 'TableUtils', '$scope'];
    function JobSubTypeCostController(JobSubTypeCostService, $log, TableUtils, $scope) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'job-subtype-cost.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job-subtype-cost.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {id: 'asc'};
        TableUtils.initTablePage(vm, JobSubTypeCostService, $scope, params);
    }
})();

