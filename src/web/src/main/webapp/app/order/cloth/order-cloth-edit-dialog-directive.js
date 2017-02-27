(function () {
    'use strict';

    angular
        .module('app')
        .directive('orderClothEditDialog', OrderClothEditDialog);

    OrderClothEditDialog.$inject = ['OrderClothService', '$log', 'EditDialogUtils', 'Utils'];

    function OrderClothEditDialog(OrderClothService, $log, EditDialogUtils, Utils) {
        return {
            templateUrl: 'app/order/cloth/order-cloth-edit-dialog.html',
            restrict: 'E',
            scope: {
                vm: '=info',
                lines: '='
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;

                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'orderLine';
                showModalParams.titleNew = 'order.cloth.table.edit.title.new';
                showModalParams.titleEdit = 'order.cloth.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                    vm.clothRequired = false;
                    vm.countWrong = false;
                    if (isNew) {
                        vm.orderLine.count = 1;
                    }
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

                    vm.clothRequired = typeof vm.orderLine.cloth == 'undefined';
                    vm.isShowRequired(vm.editForm.count);
                    vm.countWrong = false;
                    if (vm.orderLine.count != undefined) {
                        vm.countWrong = vm.orderLine.count < 1;
                    }

                    return !vm.countWrong && !vm.clothRequired;
                };

                vm.loadClothes = OrderClothService.getClothes;
                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);
                vm.filterCloth = Utils.doAutocompleteProductFilter;
            }
        }
    }
})();
