(function () {
    'use strict';

    angular
        .module('app')
        .directive('jobSubTypeEditDialog', JobSubTypeEditDialog);

    JobSubTypeEditDialog.$inject = ['JobSubTypeService', '$timeout', '$log', 'EditDialogUtils', '$window' /* , 'SECRET_EMPTY_KEY'*/];

    function JobSubTypeEditDialog(JobSubTypeService, $timeout, $log, EditDialogUtils, $window/*, SECRET_EMPTY_KEY*/) {
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

                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'jobSubType';
                showModalParams.titleNew = 'job-subtype.table.edit.title.new';
                showModalParams.titleEdit = 'job-subtype.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
//                    angular.element('.form-control')[0].triggerHandler('click')
                };

                // Configure submit
                var submitParams = {};
                submitParams.service = JobSubTypeService;
                submitParams.getEntity = function() {
                    return vm.jobSubType;
                };
                submitParams.checkInputFields = function() {
                    vm.isShowRequired(vm.editForm.name);
//                    vm.isShowRequired(vm.editForm.jobType);
                    return true;
                };

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

                vm.jobTypes = null;
                vm.getJobTypes = function (str) {
                    if (vm.jobTypes == null) {
                        vm.loadingJobTypes = true;
                        return JobSubTypeService.getJobTypes(function (data) {
                            $log.info("JobSubTypeService.getJobTypes");
                            vm.jobTypes = data;
                            vm.loadingJobTypes = false;
                            $timeout(function () {
                                var v = scope.vm.editForm.jobType.$viewValue;
                                scope.vm.editForm.jobType.$setViewValue('');
                                $timeout(function () {
                                    scope.vm.editForm.jobType.$setViewValue(v);
                                }, 0);
                            }, 0);
                            return vm.filter(str);
                        });
                    }
                    return vm.filter(str);
                };

                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);

                vm.filter = function(str) {
                    var result = [];
                    angular.forEach(vm.jobTypes, function(item) {
                        if (typeof str == 'undefined' || (item.name).toLowerCase().indexOf(('' + str).toLowerCase()) > -1) {
                            result.push(item);
                        }
                    });
                    $log.info("Str = " + str + ", Filtered: " + result);
                    return result;
                };

                vm.onSelectItem = function(item) {
                    $log.info("Selected: " + item);
                    vm.jobSubType.jobType = item;
                };


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
