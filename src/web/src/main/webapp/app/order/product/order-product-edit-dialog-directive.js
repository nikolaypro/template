(function () {
    'use strict';

    angular
        .module('app')
        .directive('orderProductEditDialog', OrderProductEditDialog);

    OrderProductEditDialog.$inject = ['OrderProductService', '$log', 'EditDialogUtils', 'Utils'];

    function OrderProductEditDialog(OrderProductService, $log, EditDialogUtils, Utils) {
        return {
            templateUrl: 'app/order/product/order-product-edit-dialog.html',
            restrict: 'E',
            scope: {
                vm: '=info',
                lines: '='
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;

                function loadStitchingTypes() {
                    OrderProductService.loadStitchingTypes(function (data) {
                        vm.stitchingTypes = data;
                        if (vm.orderLine.stitchingType == undefined) {
                            vm.orderLine.stitchingType = vm.stitchingTypes[0];
                        }
                        vm.StitchingTypesLoaded = true;
                    });
                }

                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'orderLine';
                showModalParams.titleNew = 'order.product.table.edit.title.new';
                showModalParams.titleEdit = 'order.product.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                    vm.productTypeRequired = false;
                    vm.mainClothRequired = false;
                    vm.compCloth1Required = false;
                    vm.compCloth2Required = false;
                    vm.countWrong = false;
                    if (isNew) {
                        vm.orderLine.count = 1;
                    }
                    loadStitchingTypes()
                };

                // Configure submit
                var submitParams = {};
                submitParams.submit = function update(entity, handleSuccess) {
                    if (vm.initialEntity == undefined) {
                        scope.lines.push(entity);
                    } else {
                        angular.copy(entity, vm.initialEntity);
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
                    vm.countWrong = false;
                    if (vm.orderLine.count != undefined) {
                        vm.countWrong = vm.orderLine.count < 1;
                    }

                    return !vm.countWrong && !vm.productTypeRequired && !vm.mainClothRequired && !vm.compCloth1Required && !vm.compCloth2Required;
                };

                vm.loadProducts = OrderProductService.getProducts;
                vm.loadMainCloth = function(callback) {
                    return OrderProductService.getMainCloth(vm.orderLine.product.id, callback);
                };
                vm.loadCompCloth1 = function(callback) {
                    return OrderProductService.getCompCloth1(vm.orderLine.product.id, callback);
                };
                vm.loadCompCloth2 = function(callback) {
                    return OrderProductService.getCompCloth2(vm.orderLine.product.id, callback);
                };

                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);
                vm.filterProduct = Utils.doAutocompleteProductFilter;
                vm.filterCloth = Utils.doAutocompleteProductFilter;

                scope.$watch('vm.orderLine.product', function(newVal, oldVal){
                    if (vm.orderLine != undefined && oldVal != undefined && newVal == undefined) {
                        vm.orderLine.mainCloth = undefined;
                        vm.orderLine.compCloth1 = undefined;
                        vm.orderLine.compCloth2 = undefined;
                    }
                });
            }
        }
    }
})();
