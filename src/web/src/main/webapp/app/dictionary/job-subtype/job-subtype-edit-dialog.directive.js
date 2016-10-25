(function () {
    'use strict';

    angular
        .module('app')
        .directive('jobSubTypeEditDialog', JobSubTypeEditDialog);

    JobSubTypeEditDialog.$inject = ['JobSubTypeService', '$timeout', '$log', 'Utils', 'LocMsg', 'EditDialogUtils'];

    function JobSubTypeEditDialog(JobSubTypeService, $timeout, $log, Utils, LocMsg, EditDialogUtils) {
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
                };

                // Configure submit
                var submitParams = {};
                submitParams.service = JobSubTypeService;
                submitParams.getEntity = function() {
                    return vm.jobSubType;
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
