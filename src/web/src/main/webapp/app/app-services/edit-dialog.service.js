(function () {
    'use strict';
    /**
     * ������� ���������, ������� ���������� ��� ������ ������ utils.
     *  - vm.tableParams: ����, � ������� ������ ��������� ��������� ������� ngTable
     *  - vm.editForm: ����, � ������� ������ ��������� UI �����
     *  - showModalParams.entityName: ��� ����, � ������� �������� entity (��� ����� �������� ��� ��������) ��� ��������������
     *  - showModalParams.titleNew: ��������� ���� ��� ��������
     *  - showModalParams.titleEdit: ��������� ���� ��� ��������������
     *  - showModalParams.onShow(): �������, ������� ���������� ����� �������� �����, �� �� �� ������. ������������ ���
     *    customization, ����� ���������� ������ �������������� ������
     *
     *  - submitParams.service: ������ ��� ������ ������� ��� submit (update �������)
     *  - submitParams.checkInputFields(): ������� ��� ������� ��������� �����. ���� ���������� false, �� submit �� ����� ����������
     *  - submitParams.getEntity(): ������� ��� ��������� entity, ������� ���������� ��������� �� ������
     *
     * �������� ���������, ������� ��������� � ������ ������� � ������ ���� ������������ � ���������� ���� (html �������)
     *  - vm.onModalClose(): �������, ������� ������ ���������� ��� �������� ��������� ����
     *  - vm.isShowRequired(): �������, ������� ������ ���������� ��� �������� required �����. ������������ � html.
     *  - vm.hasRequiredError: ����, ��������� � ���, ��� ������� ������ required. ������������ �
     *  - vm.showDialogFlag: ����, ���������� �� ����� �����
     *  - vm.showEditDialog(): �������, ������� ������ ���������� ��� ������ �����
     *  - vm.submit(): �������, ������� ������ ���������� ��� submit
     *  - vm.errorMessage: ����, � ������� �������� ��������� ������� ��� ��������� submit
     *  - vm.disableForm: ���� ��� disable �����. ������������ � true �� ����� submit.
     *
     *
     * ���������, ������� ��������� � �������� ����������� vm ��� ����������� �������������:
     *  - vm.element2HasError: map<String, Boolean> element.$name -> hasError ��� �������� ���������� ��������� ��������
  **/


    angular
        .module('app')
        .factory('EditDialogUtils', EditDialogUtils);

    EditDialogUtils.$inject = ['$log', '$timeout', 'LocMsg'];
    function EditDialogUtils($log, $timeout, LocMsg) {
//        var hasRequiredError = false;
        var service = {};
        service.initEditDialog = initEditDialog;
        service.showEditDialog = createShowEditDialog;
        service.submitAction = createSubmitAction;
        return service;

        function initEditDialog(vm, showModalParams, submitParams) {
            vm.element2HasError = {};

            vm.onModalClose = function() {
//                $log.info("ON CLOSE");
                vm.tableParams.reload();
            };

            vm.isShowRequired = function (el, disableParseAndValidate) {
                if (!vm.showDialogFlag || !vm.submitPressed) return false;
                if (!disableParseAndValidate) {
                    el.$$parseAndValidate();
                }
                var showRequired = $.isShowRequired(el);
//                hasRequiredError = hasRequiredError || showRequired;
                if (typeof el.$name == 'undefined') {
                    $log.error(el + ": not defined name");
                }
                vm.element2HasError[el.$name] = showRequired;
                if (showRequired) {
                    $log.info('vm.submitPressed = ' + vm.submitPressed);
                }
                return showRequired;
            };

            vm.showDialogFlag = false;
            vm.showEditDialog = createShowEditDialog(vm, showModalParams);
            vm.submit = createSubmitAction(vm, submitParams);

        }

        function createShowEditDialog(vm, params) {
            return function(entity) {
                vm.element2HasError = {};
                var isNew = typeof entity == 'undefined';
                vm.editForm.$setPristine();
                vm.editForm.$setUntouched();
                vm.errorMessage = "";
                vm.showDialogFlag = false;
                vm.disableForm = false;

                if (isNew) {
                    vm[params.entityName] = {};
                    vm.title = LocMsg.get(params.titleNew);
                } else {
                    vm[params.entityName] = angular.copy(entity);
                    vm.title = LocMsg.get(params.titleEdit);
                }
                if (typeof params.onShow != 'undefined') {
                    params.onShow(isNew);
                }

                $timeout(function () {
                    vm.showDialogFlag = true;
                    //                        scope.$digest();
                    //                        scope.$parent.$digest();
                }, 0);
            };
        }

        function createSubmitAction(vm, params) {
            return function () {
                vm.editForm.$setSubmitted();
                if (params.checkInputFields() && !hasInvalidElements(vm)) {
                    var entityCopy = params.getEntity();
                    vm.disableForm = true;
                    params.submit(entityCopy, function (data) {
                        if (data.success) {
                            $log.info("Success");
                            vm.showDialogFlag = false;
                            vm.tableParams.reload()
                        } else {
                            $log.info("Failed");
                            vm.errorMessage = data.message;
                        }
                        vm.disableForm = false;
                    });
                } else {
                    delete vm.errorMessage;
                }
            };
        }

        function hasInvalidElements(vm) {
            var result = false;
            angular.forEach(vm.element2HasError, function(e) {
                result = result || e;
            });
            return result;
        }


    }
})();