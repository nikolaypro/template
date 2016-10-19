(function () {
    'use strict';

    angular
        .module('app')
        .factory('TableUtils', TableUtils);

    TableUtils.$inject = ['$log', 'Utils', 'NgTableParams', 'ngTableEventsChannel'];
    function TableUtils($log, Utils, NgTableParams, ngTableEventsChannel) {
        var service = {};
        service.initTablePage = initTablePage;
        service.initTablePage = initTablePage;
        return service;

        function initTablePage(vm, Service) {
            Utils.refreshEditRemoveButtonEnabled(vm);
            vm.doShowEditDialog = function() {
                vm.showEditDialog(Utils.getCheckedTableRow(vm.tableParams));
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
                    LocMsg.get(vm.deleteConfirmManyMsg, rows.length):
                    LocMsg.get(vm.deleteConfirmMsg);

                Utils.showConfirm("Info", confMsg, function(dialogRef) {
                    var ids = Utils.getIds(rows);
                    Service.Delete(ids, function (data) {
                        dialogRef.close();
                        Utils.refreshEditRemoveButtonEnabled(vm);
                        if (data.data.success) {
                            $log.info("Success");
                            vm.tableParams.reload()
                        } else {
                            $log.info("Failed");
//                        BootstrapDialog.warning("Unable delete: Error message: '" + data.data.message + "'");
                            Utils.showWarning("Unable delete: Error message: '" + data.data.message + "'");
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
                        UserService.GetAll(params, function (data) {
                            params.total(data.data.total);
                            $defer.resolve(data.data.list)
                        });
                    }}
            );


        }
    }
})();