(function () {
    'use strict';

    angular
        .module('app')
        .factory('TableUtils', TableUtils);

    TableUtils.$inject = ['$log', 'Utils', 'NgTableParams', 'ngTableEventsChannel', 'LocMsg'];
    function TableUtils($log, Utils, NgTableParams, ngTableEventsChannel, LocMsg) {
        var service = {};
        service.initTablePage = initTablePage;
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
                    Service.GetById(checkedUserId, function (data) {
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
                    Service.Delete(ids, function (data) {
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

            vm.tableParams = new NgTableParams(
                {
                    page: 1,
                    count: 10,
                    sorting: {fullName: 'asc'}
                },
                {
                    total: 0,
                    getData: function ($defer, params) {
                        Service.GetAll(params, function (data) {
                            params.total(data.total);
                            $defer.resolve(data.list)
                        });
                    }}
            );


        }
    }
})();