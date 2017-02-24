(function () {
    'use strict';

    angular
        .module('app')
        .controller('OrderProductController', OrderProductController);

    OrderProductController.$inject = ['OrderProductService', '$log', 'TableUtils', '$scope', 'Utils'];
    function OrderProductController(OrderProductService, $log, TableUtils, $scope, Utils) {
        var vm = this;
        vm.isNew = false;
        var params = {};
        params.loadFromServerForEdit = false;

        vm.testOrderLines = [
            {
                product: {
                    name: "Product T 1"
                },
                mainCloth: {
                    name: "Main cloth T 1"
                },
                compCloth1: {
                    name: "Comp 1 cloth T 1"
                },
                compCloth2: {
                    name: "Comp 2 cloth T 1"
                },
                stitchingType: "Standard",
                count: 2,
                cost: 20.45
            },
            {
                product: {
                    name: "Product T 2"
                },
                mainCloth: {
                    name: "Main cloth T 2"
                },
                compCloth1: {
                    name: "Comp 1 cloth T 2"
                },
                compCloth2: {
                    name: "Comp 2 cloth T 2"
                },
                stitchingType: "Dark",
                count: 3,
                cost: 30.85
            }
        ];

        var orderLineService = {
            getAll: function(params, handleSuccess) {
                handleSuccess(TableUtils.asTableDataSource(vm.order.lines));
            },
            getById: function(id, handleSuccess) {},
            update: function(entity, handleSuccess) {
                vm.order.lines.push(entity)
            },
            delete: function(ids, handleSuccess) {}
        };

        TableUtils.initTablePage(vm, orderLineService, $scope, params);

        if (vm.isNew) {
            vm.order = {
                lines: []
            };
        } else {
            vm.order = {
                id: 4321,
                lines: vm.testOrderLines
            };
/*
show long
            OrderProductService.getById(function(data) {
                vm.order = data;
            });
*/
        }

    }
})();

