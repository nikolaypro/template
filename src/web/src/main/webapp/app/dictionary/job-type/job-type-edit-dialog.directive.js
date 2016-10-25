(function () {
    'use strict';

    angular
        .module('app')
        .directive('jobTypeEditDialog', JobTypeEditDialog);

    JobTypeEditDialog.$inject = ['JobTypeService', '$timeout', '$log', 'Utils', 'LocMsg', 'EditDialogUtils'];

    function JobTypeEditDialog(JobTypeService, $timeout, $log, Utils, LocMsg, EditDialogUtils) {
        return {
            templateUrl: 'app/dictionary/job-type/job-type-edit-dialog.html',
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
                showModalParams.entityName = 'jobType';
                showModalParams.titleNew = 'job-type.table.edit.title.new';
                showModalParams.titleEdit = 'job-type.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                };

                // Configure submit
                var submitParams = {};
                submitParams.service = JobTypeService;
                submitParams.getEntity = function() {
                    return vm.jobType;
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
