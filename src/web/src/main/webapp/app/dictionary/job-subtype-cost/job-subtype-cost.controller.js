(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobSubTypeCostController', JobSubTypeCostController);

    JobSubTypeCostController.$inject = ['JobSubTypeCostService', '$log', 'TableUtils', '$scope', '$q', 'Utils'];
    function JobSubTypeCostController(JobSubTypeCostService, $log, TableUtils, $scope, $q, Utils) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'job-subtype-cost.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job-subtype-cost.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {id: 'asc'};
        params.defaultFilter = {};

        vm.loadFilterProductNames = Utils.productFilter.loadFilterProductNames;
        vm.doFilterProduct = Utils.productFilter.doFilterProduct;

        TableUtils.initTablePage(vm, JobSubTypeCostService, $scope, params);
    }
})();

