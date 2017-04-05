(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobController', JobController);

    JobController.$inject = ['JobService', '$log', 'TableUtils', '$scope', 'Utils', 'SiteService'];
    function JobController(JobService, $log, TableUtils, $scope, Utils, SiteService) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'job.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {number: 'asc'};
        params.defaultFilter = {completeDate: Utils.getCurrDateWOTime(), showTail: vm.showTail};
        TableUtils.initTablePage(vm, JobService, $scope, params);

        vm.changeTail = function() {
            vm.tableParams.filter().showTail = !vm.tableParams.filter().showTail;
            vm.tableParams.reload();
        };

        vm.loadFilterProductNames = Utils.productFilter.loadFilterProductNames;
        vm.doFilterProduct = Utils.productFilter.doFilterProduct;


        vm.test = function() {
            $log.info("Start test");
            var items = [
                {id: 1, name: 'abc def'},// true
                {id: 1, name: ' abc def '},// true
                {id: 1, name: ' abc    def '},// true
                {id: 1, name: ' abc  gggg  def '},// true
                {id: 1, name: 'not first abc    def '},// true
                {id: 1, name: ' abc  cc ff sfasffd  def '},// true
                {id: 1, name: ' abc  cc ff sfasffd  edf '},// false
                {id: 2, name: 'bac def '},// false
                {id: 3, name: 'bbb bbb'} // false
            ];
            var result = Utils.specialItemsFilter('a d', items);
/*
            var items = [
                {id: 1, name: 'Диван Лилия шаговая Д/П М/К Ф/П вар.1'},// false
                {id: 1, name: 'Диван Лилия шаговая Д/П М/К Ф/П вар.1 метро'}// true
            ];
            var result = Utils.specialItemsFilter('див ли ша д м м', items);
*/
            angular.forEach(result, function(item) {
                $log.info(item.name);
            });
            $log.info("End test");
        };

        vm.testSequence = function() {
            $log.info("Start test sequence");
            var items = [
                {id: 1, name: 'abc def'},// true
                {id: 1, name: ' abc def '},// true
                {id: 1, name: ' abc    def '},// true
                {id: 1, name: ' abc  gggg  def '},// false
                {id: 1, name: ' abc  cc ff sfasffd  def '},// false
                {id: 1, name: ' abc  cc ff sfasffd  edf '},// false
                {id: 2, name: 'bac def '},// false
                {id: 3, name: 'bbb bbb'}, // false
                {id: 3, name: ' ad '} // false
            ];
            var result = Utils.specialItemsFilterWithSequence('a d', items);
            angular.forEach(result, function(item) {
                $log.info(item.name);
            });
            $log.info("End test");


            $log.info("Start test sequence real data");
            items = [
                {id: 1, name: 'Диван Лилия шаговая Д/П М/К Ф/П вар.1'},// false
                {id: 1, name: 'Диван Лилия шаговая Д/П М/К Ф/П вар.1 метро'},// false
                {id: 1, name: 'Диван Лилия шаговая Д/П М/К Малина Ф/П вар.1 метро'},// true
                {id: 1, name: 'Диван Лилия шаговая Д/П М/К '}// false
            ];
            result = Utils.specialItemsFilterWithSequence('див ли ша д м м', items);
            angular.forEach(result, function(item) {
                $log.info(item.name);
            });
            $log.info("End test");

        };
    }
})();

