(function () {
    'use strict';

    angular
        .module('app')
        .controller('OrdersController', OrdersController);

    OrdersController.$inject = ['OrdersService', '$log', 'TableUtils', '$scope', 'Utils', '$location'];
    function OrdersController(OrdersService, $log, TableUtils, $scope, Utils, $location) {
        var vm = this;
        vm.dateFormat = Utils.getDateFormat();
        var params = {};
        params.defaultSort = {id: 'asc'};
        params.defaultFilter = {};
        TableUtils.initTablePage(vm, OrdersService, $scope, params);
        vm.showOrder = function(id, type) {
            if (type == 'PRODUCT') {
                $location.path('order-product').search({id: id});
            } else if (type == 'CLOTH') {
                $location.path('order-cloth').search({id: id});
            } else {
                alert('Unknows order type "' + type + '"')
            }
        }
    }
})();

