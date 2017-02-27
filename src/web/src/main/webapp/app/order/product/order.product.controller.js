(function () {
    'use strict';

    angular
        .module('app')
        .controller('OrderProductController', OrderProductController);

    OrderProductController.$inject = ['OrderProductService', '$log', 'TableUtils', '$scope', 'Utils', '$location'];
    function OrderProductController(OrderProductService, $log, TableUtils, $scope, Utils, $location) {
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
            OrderProductService.getById($location.search().id, function(data) {
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
            OrderProductService.update(vm.order, function(data) {
                if (data != undefined) {
                    $location.path('order-product').search({id: data});
                }
            })
        };
        vm.saveOrderAndSend = function() {
            vm.order.send = true;
            vm.order.cost = -11111;
            angular.forEach(vm.order.lines, function(e) {
                e.cost = -11;
            });
            OrderProductService.update(vm.order, function(data) {
                if (data != undefined) {
                    $location.path('order-product').search({id: data});
                }
            })
        };

        vm.isEditable = function() {
            return vm.isNew || vm.order != undefined && !vm.order.send;
        };

    }
})();

