(function () {
    'use strict';

    angular
        .module('app')
        .controller('OrderClothController', OrderClothController);

    OrderClothController.$inject = ['OrderClothService', '$log', 'TableUtils', '$scope', 'Utils', '$location'];
    function OrderClothController(OrderClothService, $log, TableUtils, $scope, Utils, $location) {
        var vm = this;
        vm.isNew = $location.search().id == undefined;
        var params = {};
        var rowsProvider = {
            getRows: function() {
                if (vm.order == undefined) {
                    return undefined;
                }
                return vm.order.lines;
            },
            setRows: function(rows) {
                vm.order.lines = rows;
            }
        };

        TableUtils.initInMemoryTablePage(vm, $scope, params, rowsProvider);

        if (vm.isNew) {
            vm.order = {
                lines: []
            };
        } else {
            OrderClothService.getById($location.search().id, function(data) {
                vm.order = data;
                vm.tableParams.reload();
            });
        }

        vm.saveOrderOnly = function() {
            vm.order.send = false;
            vm.order.cost = -11111;
            angular.forEach(vm.order.lines, function(e) {
                e.cost = -11;
            });
            OrderClothService.update(vm.order, function(data) {
                if (data != undefined) {
                    $location.path('order-cloth').search({id: data});
                }
            })
        };
        vm.saveOrderAndSend = function() {
            vm.order.send = true;
            vm.order.cost = -11111;
            angular.forEach(vm.order.lines, function(e) {
                e.cost = -11;
            });
            OrderClothService.update(vm.order, function(data) {
                if (data != undefined) {
                    $location.path('order-cloth').search({id: data});
                }
            })
        };

        vm.isEditable = function() {
            return vm.isNew || vm.order != undefined && !vm.order.send;
        };

    }
})();

