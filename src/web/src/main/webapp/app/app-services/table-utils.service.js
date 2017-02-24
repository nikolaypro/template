(function () {
    'use strict';
    /**
     * ������� ���������, ������� ���������� ��� ������ ������ utils.
     *  - params.loadFromServerForEdit: ����, ���������� �� �������� � ������� ������ ���������� entity ���
     *    ������������� �����������. ����� ���� ������������ � ��� �������, ����� � ������� ������������ (�����������)
     *    �� ��� ����, � ��� �������������� ���������� ��� ����, ������� ��������� �������������� ������.
     *  - vm.editDisabled: ����, ���������� �� enable ������ ��������������
     *  - vm.removeDisabled: ����, ���������� �� enable ������ ��������
     *  - vm.showEditDialog(): ������� �������� ���� ��������/��������������
     *  - params.deleteConfirmManyMsg: Key ��� ��������� �� �������� ������ ���������
     *  - params.deleteConfirmMsg: Key ��� ��������� �� �������� ������ ��������
     *  - params.defaultSort: default ���������� ��� �������
     *  - params.onDataLoaded(): �������, ������������ ����� �������� ������
     *  - Service: ������ ��� ������ ������� �������: getById, getAll, delete
     *
     *
     *
     * �������� ���������, ������� ��������� � ������ ������� � ������ ���� ������������ � ���������� ���� (html �������)
     *  - vm.tableParams: ��������� �������
     *
     *
     *  - vm.onModalClose(): �������, ������� ������ ���������� ��� �������� ��������� ����
     *  - vm.isShowRequired(): �������, ������� ������ ���������� ��� �������� required �����. ������������ � html.
     *  - vm.hasRequiredError: ����, ��������� � ���, ��� ������� ������ required. ������������ �
     *  - vm.showDialogFlag: ����, ���������� �� ����� �����
     *  - vm.showEditDialog(): �������, ������� ������ ���������� ��� ������ �����
     *  - vm.submit(): �������, ������� ������ ���������� ��� submit
     *  - vm.errorMessage: ����, � ������� �������� ��������� ������� ��� ��������� submit
     *  - vm.disableForm: ���� ��� disable �����. ������������ � true �� ����� submit.
     *
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
     * ���������, ������� ��������� � �������� ����������� vm ��� ����������� �������������:
     *  - vm.element2HasError: map<String, Boolean> element.$name -> hasError ��� �������� ���������� ��������� ��������
     **/

    angular
        .module('app')
        .factory('TableUtils', TableUtils);

    TableUtils.$inject = ['$log', 'Utils', 'NgTableParams', 'ngTableEventsChannel', 'LocMsg'];
    function TableUtils($log, Utils, NgTableParams, ngTableEventsChannel, LocMsg) {
        var service = {};
        service.initTablePage = initTablePage;
        service.asTableDataSource = asTableDataSource;
        service.unCheckTableRows = unCheckTableRows;
        return service;

        function initTablePage(vm, Service, $scope, params) {
            params = params || {};
            if (typeof params.loadFromServerForEdit == 'undefined') {
                params['loadFromServerForEdit'] = true;
            }

            Utils.refreshEditRemoveButtonEnabled(vm);
            vm.tableLoading = false;
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
                    sorting: params.defaultSort,
                    filter: params.defaultFilter,
                    filterOptions: { filterLayout: "horizontal" }
                },
                {
                    total: 0,
                    getData: function ($defer, params) {
                        vm.tableLoading = true;
                        Service.getAll(params, function (data) {
                            params.total(data.total);
                            if (typeof superParams.onDataLoaded != 'undefined') {
                                superParams.onDataLoaded(params, data);
                            }
                            vm.tableLoading = false;
                            $defer.resolve(data.list)
                        });
                    }}
            );
        }

        function asTableDataSource(list) {
            return {
                list: list,
                total: list.length
            }
        }

        function unCheckTableRows(list) {
            angular.forEach(list, function (row) {
                row.row_checked = false;
            });
        }



    }
})();