(function () {
    'use strict';

    angular
        .module('app')
        .controller('OrderController', OrderController);

    OrderController.$inject = ['OrderService', '$log', 'TableUtils', '$scope', 'Utils'];
    function OrderController(OrderService, $log, TableUtils, $scope, Utils) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'order.table.delete.confirm.many';
        params.deleteConfirmMsg = 'order.table.delete.confirm';
        params.loadFromServerForEdit = false;
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
             {id: 1, name: '����� ����� ������� �/� �/� �/� ���.1'},// false
             {id: 1, name: '����� ����� ������� �/� �/� �/� ���.1 �����'}// true
             ];
             var result = Utils.specialItemsFilter('��� �� �� � � �', items);
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
                {id: 1, name: '����� ����� ������� �/� �/� �/� ���.1'},// false
                {id: 1, name: '����� ����� ������� �/� �/� �/� ���.1 �����'},// false
                {id: 1, name: '����� ����� ������� �/� �/� ������ �/� ���.1 �����'},// true
                {id: 1, name: '����� ����� ������� �/� �/� '}// false
            ];
            result = Utils.specialItemsFilterWithSequence('��� �� �� � � �', items);
            angular.forEach(result, function(item) {
                $log.info(item.name);
            });
            $log.info("End test");

        }

    }
})();

