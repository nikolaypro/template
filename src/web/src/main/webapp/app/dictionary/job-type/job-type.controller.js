(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobTypeController', JobTypeController);

    JobTypeController.$inject = ['JobTypeService', '$log', 'TableUtils', '$scope'];
    function JobTypeController(JobTypeService, $log, TableUtils, $scope) {
        var vm = this;
        var params = {};
        params.onDataLoaded = function(params, data) {
            if (data.list.length > 0) {
                vm.pageCount = Math.ceil(vm.tableParams.total() / vm.tableParams.count());
            }
        };
        params.deleteConfirmManyMsg = 'job-type.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job-type.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {order: 'asc'};
        TableUtils.initTablePage(vm, JobTypeService, $scope, params);

        var moveCallBack = function() {
            vm.tableParams.reload();
        };

        vm.onDown = function(row) {
            JobTypeService.moveDown(row.id, moveCallBack);
        };

        vm.onUp = function(row, page, index) {
            $log.info('Page = ' + page + ", index = " + index);
            JobTypeService.moveUp(row.id, moveCallBack);
        }

    }
})();

