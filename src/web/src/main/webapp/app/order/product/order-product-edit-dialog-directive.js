(function () {
    'use strict';

    angular
        .module('app')
        .directive('orderProductEditDialog', OrderProductEditDialog);

    OrderProductEditDialog.$inject = ['OrderService', '$log', 'EditDialogUtils', 'Utils'];

    function OrderProductEditDialog(OrderService, $log, EditDialogUtils, Utils) {
        return {
            templateUrl: 'app/order/product/order-product-edit-dialog.html',
            restrict: 'E',
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;

                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'orderLine';
                showModalParams.titleNew = 'order.product.table.edit.title.new';
                showModalParams.titleEdit = 'order.product.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                    vm.jobTypeRequired = false;
                    vm.productTypeRequired = false;
                    if (isNew) {
                        vm.orderLine.count = 1;
                    }
                };

                // Configure submit
                var submitParams = {};
                submitParams.submit = function update(entity, handleSuccess) {
                    if (isNew) {
                        vm.orderLines.list.push(entity);
                    }
                    handleSuccess({success: true});
                };
                submitParams.getEntity = function() {
                    return vm.orderLine;
                };
                submitParams.checkInputFields = function() {
                    vm.productTypeRequired = typeof vm.orderLine.product == 'undefined';
                    vm.mainClothRequired = typeof vm.orderLine.mainCloth == 'undefined';
                    vm.compCloth1Required = typeof vm.orderLine.compCloth1 == 'undefined';
                    vm.compCloth2Required = typeof vm.orderLine.compCloth2 == 'undefined';

                    vm.isShowRequired(vm.editForm.count);

                    return !vm.productTypeRequired && !vm.mainClothRequired && !vm.compCloth1Required && !vm.compCloth2Required;
                };

                vm.loadProducts = OrderService.getProducts;
                vm.loadMainCloth = function(callback) {
                    return OrderService.getMainCloth(vm.orderLine.product, callback);
                };
                vm.loadCompCloth1 = function(callback) {
                    return OrderService.getCompCloth1(vm.orderLine.product, callback);
                };
                vm.loadCompCloth2 = function(callback) {
                    return OrderService.getCompCloth2(vm.orderLine.product, callback);
                };

                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);
                vm.filterProduct = Utils.doAutocompleteProductFilter;
                vm.filterCloth = Utils.doAutocompleteProductFilter;
            }
        }
    }
})();
