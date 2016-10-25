(function () {
    'use strict';

    angular
        .module('app')
        .directive('productEditDialog', ProductEditDialog);

    ProductEditDialog.$inject = ['ProductService', '$timeout', '$log', 'Utils', 'LocMsg', 'EditDialogUtils'];

    function ProductEditDialog(ProductService, $timeout, $log, Utils, LocMsg, EditDialogUtils) {
        return {
            templateUrl: 'app/dictionary/product/product-edit-dialog.html',
            restrict: 'E',
//            transclude: true,
//            replace: true,
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;

                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'product';
                showModalParams.titleNew = 'product.table.edit.title.new';
                showModalParams.titleEdit = 'product.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                };

                // Configure submit
                var submitParams = {};
                submitParams.service = ProductService;
                submitParams.getEntity = function() {
                    return vm.product;
                };
                submitParams.checkInputFields = function() {
                    vm.isShowRequired(vm.editForm.name);
                    return true;
                };

                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);

            }
        }
    }
})();
