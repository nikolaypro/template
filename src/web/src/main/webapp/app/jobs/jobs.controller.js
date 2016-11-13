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
        params.defaultSort = {id: 'asc'};
        params.defaultFilter = {completeDate: Utils.getCurrDateWOTime()};
        vm.datePeriodFilter = Utils.getCurrDateWOTime();
        vm.dateFormat = Utils.getDateFormat();
        vm.getStartWeek = function(date) {
            var first = date.getDay() == 0 ? (date.getDate() - 6) : (date.getDate() - date.getDay() + 1); // First day is the day of the month - the day of the week

            return new Date(new Date(date).setDate(first));
        };
        vm.getEndWeek = function(date) {
            var startWeek = vm.getStartWeek(date);
            var last = startWeek.getDate() + 6;
            return new Date(new Date(startWeek).setDate(last));
        };
        TableUtils.initTablePage(vm, JobService, $scope, params);

    }
})();

