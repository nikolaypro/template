(function () {
    'use strict';

    angular
        .module('app')
        .directive('jobSubTypeCostEditDialog', JobSubTypeCostEditDialog);

    JobSubTypeCostEditDialog.$inject = ['JobSubTypeCostService', '$log', 'EditDialogUtils', 'Utils'];

    function JobSubTypeCostEditDialog(JobSubTypeCostService, $log, EditDialogUtils, Utils) {
        return {
            templateUrl: 'app/dictionary/job-subtype-cost/job-subtype-cost-edit-dialog.html',
            restrict: 'E',
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;

                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'jobSubTypeCost';
                showModalParams.titleNew = 'job-subtype-cost.table.edit.title.new';
                showModalParams.titleEdit = 'job-subtype-cost.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                    vm.jobSubTypeRequired = false;
                    vm.productTypeRequired = false;
//                    angular.element('.form-control')[0].triggerHandler('click')
                };

                // Configure submit
                var submitParams = {};
                submitParams.submit = function(entity, callback) {
                    if (entity.id) {
                        JobSubTypeCostService.update(entity, callback);
                    } else {
                        Utils.callCheckBeforeInvokeService(
                            JobSubTypeCostService.checkNotExists,
                            JobSubTypeCostService.update,
                            JobSubTypeCostService.updateCostOnly, [entity], callback, "job-subtype-cost.already.exists.cost.update.question");
                        }
                };
                submitParams.getEntity = function() {
                    return vm.jobSubTypeCost;
                };
                submitParams.checkInputFields = function() {
                    vm.jobSubTypeRequired = typeof vm.jobSubTypeCost.jobSubType == 'undefined';
                    vm.productTypeRequired = typeof vm.jobSubTypeCost.product == 'undefined';
                    vm.isShowRequired(vm.editForm.cost);
                    return !vm.jobSubTypeRequired && !vm.productTypeRequired;
                };

                scope.$watch('vm.jobSubTypeCost.jobSubType', function(newVal, oldVal){
                    vm.jobSubTypeRequired = false;
                });
                scope.$watch('vm.jobSubTypeCost.product', function(newVal, oldVal){
                    vm.productTypeRequired = false;
                });

                vm.loadJobSubTypes = JobSubTypeCostService.getJobSubTypes;
                vm.loadProducts = JobSubTypeCostService.getProducts;
                vm.getComboItemPrefix = function(item) {
                    return item.jobType.name;
                };


/*
//    TEST Utils.callCheckBeforeInvokeService
                var checkFn = function(a, b, c, callback) {
                    $log.info("CHECK: a = " + a + ", b = " + b + ", c = " + c + ", callback: " + (typeof callback));
                    // callback({result: false});
                    callback({result: true});
                };

                var trueFn = function(a, b, c, callback) {
                    $log.info("TRUE: a = " + a + ", b = " + b + ", c = " + c + ", callback: " + callback);
                };

                var falseFn = function(a, b, c, callback) {
                    $log.info("FALSE: a = " + a + ", b = " + b + ", c = " + c + ", callback: " + callback);
                };

                Utils.callCheckBeforeInvokeService(checkFn, trueFn, falseFn, ["1", "2", "3"], function (){}, "Update?");
*/

                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);

                vm.filterProduct = Utils.specialItemsFilter;


                /*
                 vm.onFocus = function (e) {
                 vm.e = e;
                 $timeout(function () {
                 $(e.target).trigger('input');
                 $(e.target).trigger('click');
                 //                        $(e.target).trigger('focus');
                 //                        $(e.target).trigger('change'); // for IE
                 });
                 };
                 */

            }
        }
    }
})();
