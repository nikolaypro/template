(function () {
    'use strict';

    angular
        .module('app')
        .controller('ProductsController', ProductsController);

    ProductsController.$inject = ['ProductService', '$log', 'TableUtils', '$scope'];
    function ProductsController(ProductService, $log, TableUtils, $scope) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'product.table.delete.confirm.many';
        params.deleteConfirmMsg = 'product.table.delete.confirm';
        params.loadFromServerForEdit = false;
        params.defaultSort = {name: 'asc'};
        TableUtils.initTablePage(vm, ProductService, $scope, params);
    }
})();

