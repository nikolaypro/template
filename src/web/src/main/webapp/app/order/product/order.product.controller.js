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

        vm.testOrderLines = {
            list: [
            {
                product: {
                    name: "Product 1"
                },
                mainCloth: {
                    name: "Main cloth 1"
                },
                compCloth1: {
                    name: "Comp 1 cloth 1"
                },
                compCloth2: {
                    name: "Comp 2 cloth 1"
                },
                stitchingType: "Standard",
                count: 2,
                cost: 20.45
            },
            {
                product: {
                    name: "Product 2"
                },
                mainCloth: {
                    name: "Main cloth 2"
                },
                compCloth1: {
                    name: "Comp 1 cloth 2"
                },
                compCloth2: {
                    name: "Comp 2 cloth 2"
                },
                stitchingType: "Dark",
                count: 3,
                cost: 30.85
            }
        ], total: 100};

        var orderLineService = {
            getAll: function(params, handleSuccess) {
                handleSuccess(vm.order.lines);
            },
            getById: function(id, handleSuccess) {},
            update: function(entity, handleSuccess) {},
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
            OrderProductService.getById(function(data) {
                vm.order = data;
            });
*/
        }

    }
})();

