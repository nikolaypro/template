(function () {
    'use strict';
    /**
     * Входные параметры, которые необходимы для работы данной utils.
     *  - params.loadFromServerForEdit: флаг, отвечающий за загрузку с сервера нового экземпляра entity или
     *    использование переданного. Может быть использовано в тех случаях, когда в таблице отображаются (загружаются)
     *    не все поля, а при редактировании необходимы все поля, поэтому необходим дополнительный запрос.
     *  - vm.editDisabled: флаг, отвечающий на enable кнопки редактирования
     *  - vm.removeDisabled: флаг, отвечающий на enable кнопки удаления
     *  - vm.showEditDialog(): функция открытия окна создания/редактирования
     *  - params.deleteConfirmManyMsg: Key для сообщения об удалении многих элементов
     *  - params.deleteConfirmMsg: Key для сообщения об удалении одного элемента
     *  - params.defaultSort: default сортировка для таблицы
     *  - params.onDataLoaded(): фуекция, вызывающееся после загрузки данных
     *  - Service: сервис для вызова методов сервера: getById, getAll, delete
     *
     *
     *
     * Выходные параметры, которые создаются в данной утилите и должны быть использованы в вызывающем коде (html шаблоне)
     *  - vm.tableParams: параметры таблицы
     *
     *
     *  - vm.onModalClose(): Функция, которая должна вызываться при закрытии диалового окна
     *  - vm.isShowRequired(): функция, которая должна вызываться при проверке required полей. Используется в html.
     *  - vm.hasRequiredError: флаг, говорящий о том, что имеются ошибки required. Используется в
     *  - vm.showDialogFlag: флаг, отвечающий за показ формы
     *  - vm.showEditDialog(): функция, которая должна вызываться для показа формы
     *  - vm.submit(): функция, которая должна вызываться для submit
     *  - vm.errorMessage: поле, в котором хранится сообщение сервера при неудачном submit
     *  - vm.disableForm: флаг для disable формы. Выставляется в true во время submit.
     *
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
     * Параметры, которые создаются в конексте переданного vm для внутреннего использования:
     *  - vm.element2HasError: map<String, Boolean> element.$name -> hasError для хранения последнего состояния элемента
     **/

    angular
        .module('app')
        .factory('TableUtils', TableUtils);

    TableUtils.$inject = ['$log', 'Utils', 'NgTableParams', 'ngTableEventsChannel', 'LocMsg'];
    function TableUtils($log, Utils, NgTableParams, ngTableEventsChannel, LocMsg) {
        var service = {};
        service.initTablePage = initTablePage;
        return service;

        function initTablePage(vm, Service, $scope, params) {
            params = params || {};
            if (typeof params.loadFromServerForEdit == 'undefined') {
                params['loadFromServerForEdit'] = true;
            }

            Utils.refreshEditRemoveButtonEnabled(vm);
            vm.doShowNewDialog = function() {
                vm.showEditDialog();
            };
            vm.doShowEditDialog = function() {
                var checkedUser = Utils.getCheckedTableRow(vm.tableParams);
                if (params.loadFromServerForEdit) {
                    var checkedUserId = checkedUser.id;
                    Service.getById(checkedUserId, function (data) {
                        vm.showEditDialog(data);
                    });
                } else {
                    vm.showEditDialog(checkedUser);
                }
            };

            ngTableEventsChannel.onAfterReloadData(function() {
                $log.info("RELOAD TABLE DATA");
                Utils.refreshEditRemoveButtonEnabled(vm);
            }, $scope, function(publishingParams) {
                return vm.tableParams === publishingParams
            });


            vm.onRowChecked = function() {
                Utils.refreshEditRemoveButtonEnabled(vm, vm.tableParams);
            };

            vm.doRemove = function() {
                $log.info('do remove');
                var rows = Utils.getCheckedTableRows(vm.tableParams);
                if (rows.length == 0) {
                    BootstrapDialog.warning("Select product to delete please");
                    return;
                }
                var confMsg = rows.length > 1 ?
                    LocMsg.get(params.deleteConfirmManyMsg, rows.length):
                    LocMsg.get(params.deleteConfirmMsg);

                Utils.showConfirm("Info", confMsg, function(dialogRef) {
                    var ids = Utils.getIds(rows);
                    Service.delete(ids, function (data) {
                        dialogRef.close();
                        Utils.refreshEditRemoveButtonEnabled(vm);
                        if (data.success) {
                            $log.info("Success");
                            vm.tableParams.reload()
                        } else {
                            $log.info("Failed");
//                        BootstrapDialog.warning("Unable delete: Error message: '" + data.data.message + "'");
                            Utils.showWarning("Unable delete: Error message: '" + data.message + "'");
                        }
                    });
                });
            };
            var superParams = params;
            vm.tableParams = new NgTableParams(
                {
                    page: 1,
                    count: 10,
                    sorting: params.defaultSort
                },
                {
                    total: 0,
                    getData: function ($defer, params) {
                        Service.getAll(params, function (data) {
                            params.total(data.total);
                            if (typeof superParams.onDataLoaded != 'undefined') {
                                superParams.onDataLoaded(params, data);
                            }
                            $defer.resolve(data.list)
                        });
                    }}
            );


        }
    }
})();