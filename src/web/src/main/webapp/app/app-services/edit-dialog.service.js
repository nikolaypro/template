(function () {
    'use strict';
    /**
     * Входные параметры, которые необходимы для работы данной utils.
     *  - vm.tableParams: поле, в которой должна храниться параметры таблицы ngTable
     *  - vm.editForm: поле, в которой должна храниться UI форма
     *  - showModalParams.entityName: имя поля, в котором хранится entity (или будет хранится при создании) для редактирования
     *  - showModalParams.titleNew: заголовок окна при создании
     *  - showModalParams.titleEdit: заголовок окна при редактировании
     *  - showModalParams.onShow(): функция, которая вызывается после создании формы, но до ее показа. Используется для
     *    customization, когда необходимо задать дополнительную логику
     *
     *  - submitParams.service: сервис для вызова сервера для submit (update функция)
     *  - submitParams.checkInputFields(): функция для проврки введенных полей. Если возвращает false, то submit не будет вызываться
     *  - submitParams.getEntity(): функция для получения entity, которую необходимо отправить на сервер
     *
     * Выходные параметры, которые создаются в данной утилите и должны быть использованы в вызывающем коде (html шаблоне)
     *  - vm.onModalClose(): Функция, которая должна вызываться при закрытии диалового окна
     *  - vm.isShowRequired(): функция, которая должна вызываться при проверке required полей. Используется в html.
     *  - vm.hasRequiredError: флаг, говорящий о том, что имеются ошибки required. Используется в
     *  - vm.showDialogFlag: флаг, отвечающий за показ формы
     *  - vm.showEditDialog(): функция, которая должна вызываться для показа формы
     *  - vm.submit(): функция, которая должна вызываться для submit
     *  - vm.errorMessage: поле, в котором хранится сообщение сервера при неудачном submit
     *  - vm.disableForm: флаг для disable формы. Выставляется в true во время submit.
     *
     *
     * Параметры, которые создаются в конексте переданного vm для внутреннего использования:
     *  - vm.element2HasError: map<String, Boolean> element.$name -> hasError для хранения последнего состояния элемента
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
                    params.service.update(entityCopy, function (data) {
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