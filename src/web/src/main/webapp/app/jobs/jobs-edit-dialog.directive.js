(function () {
    'use strict';

    angular
        .module('app')
        .directive('jobEditDialog', JobEditDialog);

    JobEditDialog.$inject = ['JobService', '$log', 'EditDialogUtils', 'Utils'];

    function JobEditDialog(JobService, $log, EditDialogUtils, Utils) {
        return {
            templateUrl: 'app/jobs/jobs-edit-dialog.html',
            restrict: 'E',
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;

                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'job';
                showModalParams.titleNew = 'job.table.edit.title.new';
                showModalParams.titleEdit = 'job.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                    vm.jobTypeRequired = false;
                    vm.productTypeRequired = false;
                    if (isNew) {
                        vm.job.completeDate = Utils.getCurrDateWOTime();
                        if (vm.lastCompleteDate != undefined) {
                            vm.job.completeDate = vm.lastCompleteDate != undefined ?
                                vm.lastCompleteDate :
                                Utils.getCurrDateWOTime();
                        }
                        vm.job.jobType = vm.lastJobTypeSelected;
                        if (vm.lastNumber != undefined) {
                            vm.job.number = vm.lastNumber + 1;
                        }
                        if (vm.lastProductSelected != undefined) {
                            vm.fixedProduct = vm.lastProductSelected;
                        }
                    }
                };

                // Configure submit
                var submitParams = {};
                submitParams.submit = JobService.update;
                submitParams.getEntity = function() {
                    return vm.job;
                };
                submitParams.checkInputFields = function() {
                    vm.jobTypeRequired = typeof vm.job.jobType == 'undefined';
                    vm.productTypeRequired = typeof vm.job.product == 'undefined';
                    vm.isShowRequired(vm.editForm.number);

//                    vm.isShowRequired(vm.editForm.completeDate);
                    return !vm.jobTypeRequired && !vm.productTypeRequired && Utils.parseDate(vm.job.completeDate) != undefined;
                };

                scope.$watch('vm.job.number', function(newVal, oldVal){
                    vm.lastNumber = newVal;
                });

                scope.$watch('vm.job.completeDate', function(newVal, oldVal){
                    vm.lastCompleteDate = newVal;
                });

                scope.$watch('vm.job.jobType', function(newVal, oldVal){
                    vm.jobTypeRequired = false;
                    vm.lastJobTypeSelected = newVal;
                });
                scope.$watch('vm.job.product', function(newVal, oldVal){
                    vm.productTypeRequired = false;
                    vm.lastProductSelected = newVal;
                });

                vm.loadJobTypes = JobService.getJobTypes;
                vm.loadProducts = JobService.getProducts;
                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);

                vm.dateFormat = Utils.getDateFormat();
                vm.validDate = function(date) {
                    return Utils.isValidDate(vm, date);
                };
                vm.completeDateOptions = {
//                    dateDisabled: disabled,
                    formatYear: 'yy',
                    maxDate: new Date(2020, 1, 1),
                    minDate: new Date(2016, 1, 1),
                    startingDay: 1
                };
                vm.filterProduct = Utils.doAutocompleteProductFilter;
            }
        }
    }
})();
