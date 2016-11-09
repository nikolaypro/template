(function () {
    'use strict';

    angular
        .module('app')
        .directive('jobEditDialog', JobEditDialog);

    JobEditDialog.$inject = ['JobService', '$log', 'EditDialogUtils', 'Utils'];

    function JobEditDialog(JobService, $log, EditDialogUtils, Utils) {
        return {
            templateUrl: 'app/jobs/job-edit-dialog.html',
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
                    vm.isShowRequired(vm.editForm.completeDate);
                    return !vm.jobTypeRequired && !vm.productTypeRequired;
                };

                scope.$watch('vm.job.jobType', function(newVal, oldVal){
                    vm.jobTypeRequired = false;
                });
                scope.$watch('vm.job.product', function(newVal, oldVal){
                    vm.productTypeRequired = false;
                });

                vm.loadJobTypes = JobService.getJobTypes;
                vm.loadProducts = JobService.getProducts;
                vm.typeVm = {};
                vm.typeVm.placeHolder = 'job.table.edit.job-type';
                vm.productVm = {};
                vm.productVm.placeHolder = 'job.table.edit.product';
                // vm.placeHolder = 'job-subtype-cost.table.edit.job-subtype';

                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);

            }
        }
    }
})();
