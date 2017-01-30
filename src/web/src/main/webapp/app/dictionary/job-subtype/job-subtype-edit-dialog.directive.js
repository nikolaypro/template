(function () {
    'use strict';

    angular
        .module('app')
        .directive('jobSubTypeEditDialog', JobSubTypeEditDialog);

    JobSubTypeEditDialog.$inject = ['JobSubTypeService', 'EditDialogUtils', '$rootScope'];

    function JobSubTypeEditDialog(JobSubTypeService, EditDialogUtils, $rootScope) {
        return {
            templateUrl: 'app/dictionary/job-subtype/job-subtype-edit-dialog.html',
            restrict: 'E',
//            transclude: true,
//            replace: true,
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;
                vm.reportGroupEnabled = $rootScope.globals.settings.reportGroupEnabled;


                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'jobSubType';
                showModalParams.titleNew = 'job-subtype.table.edit.title.new';
                showModalParams.titleEdit = 'job-subtype.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                    vm.jobTypeRequired = false;
                    if (isNew) {
                        vm.jobSubType.useInSalaryReport = true;
                    }
//                    angular.element('.form-control')[0].triggerHandler('click')
                };

                // Configure submit
                var submitParams = {};
                submitParams.submit = JobSubTypeService.update;
                submitParams.getEntity = function() {
                    return vm.jobSubType;
                };
                submitParams.checkInputFields = function() {
                    vm.isShowRequired(vm.editForm.name);
                    vm.jobTypeRequired = typeof vm.jobSubType.jobType == 'undefined';
                    return !vm.jobTypeRequired;
                };

                scope.$watch('vm.jobSubType.jobType', function(newVal, oldVal){
                    console.log("Search was changed to:" + newVal);
                    vm.jobTypeRequired = false;
                });
/*
variant 1
                var jobTypes = null;
                vm.loadingJobTypes = true;
                JobSubTypeService.getJobTypes(function (data) {
                    jobTypes = data;
                    vm.loadingJobTypes = false;
                });
                vm.getJobTypes = function (str) {
                    return jobTypes;
                };
*/


                vm.loadJobTypes = JobSubTypeService.getJobTypes;
//                vm.placeHolder = 'job-subtype.table.edit.job-type';

                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);



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
