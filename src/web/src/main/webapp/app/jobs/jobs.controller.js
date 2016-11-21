(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobController', JobController);

    JobController.$inject = ['JobService', '$log', 'TableUtils', '$scope', 'Utils'];
    function JobController(JobService, $log, TableUtils, $scope, Utils) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'job.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {completeDate: 'asc'};
        params.defaultFilter = {completeDate: Utils.getCurrDateWOTime(), showTail: vm.showTail};
        TableUtils.initTablePage(vm, JobService, $scope, params);

        vm.changeTail = function() {
            vm.tableParams.filter().showTail = !vm.tableParams.filter().showTail;
            vm.tableParams.reload();
        };


    }
})();

