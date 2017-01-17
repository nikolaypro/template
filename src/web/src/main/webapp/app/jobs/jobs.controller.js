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

        vm.test = function() {
            $log.info("Start test");
            var items = [
                {id: 1, name: 'abc def'},// true
                {id: 1, name: ' abc def '},// true
                {id: 1, name: ' abc    def '},// true
                {id: 1, name: ' abc  gggg  def '},// true
                {id: 1, name: ' abc  cc ff sfasffd  def '},// true
                {id: 1, name: ' abc  cc ff sfasffd  edf '},// false
                {id: 2, name: 'bac def '},// false
                {id: 3, name: 'bbb bbb'} // false
            ];
            var result = Utils.specialItemsFilter('a d', items);

            angular.forEach(result, function(item) {
                $log.info(item.name);
            });

            $log.info("End test");


        }


    }
})();

