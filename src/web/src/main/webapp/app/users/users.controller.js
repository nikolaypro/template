(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController)
        .controller('tt', tt);

    UsersController.$inject = ['UserService', 'NgTableParams', '$scope', '$timeout', '$log', 'ALL_APP_ROLES', 'Utils',
        '$window', 'ngDialog', 'LocMsg', 'ngTableEventsChannel', 'TableUtils'];
    function UsersController(UserService, NgTableParams, $scope, $timeout, $log, ALL_APP_ROLES,
                             Utils, $window, ngDialog, LocMsg, ngTableEventsChannel, TableUtils) {
        var vm = this;
        vm.roles = ALL_APP_ROLES;
        vm.formatRoles = function(roles) {
            var result = '';
            angular.forEach(roles, function(role) {
                if (result.length > 0) {
                    result += ", "
                }
                result += LocMsg.get(role);
            });
            return result;
        };

        var params = {
            deleteConfirmManyMsg:  'user.table.delete.confirm.many',
            deleteConfirmMsg : 'user.table.delete.confirm',
            loadFromServerForEdit: false
        };
        TableUtils.initTablePage(vm, UserService, $scope, params);

/*
        Utils.refreshEditRemoveButtonEnabled(vm);
        vm.doShowUserDialog = function() {
            vm.showUserDialog(Utils.getCheckedTableRow(vm.tableParams));
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
                BootstrapDialog.warning("Select user to delete please");
                return;
            }
            var confMsg = rows.length > 1 ?
                LocMsg.get('user.table.delete.confirm.many', rows.length):
                LocMsg.get('user.table.delete.confirm');

//            Another way to open modal confirmation dialog
//            ngDialog.openConfirm({ template: 'app/template/dialogs/yes-no-dialog.template.html', className: 'ngdialog-theme-default'}).then(function(value) {
//                $log.info("selected OK: " + value);
//            });

            Utils.showConfirm("Info", confMsg, function(dialogRef) {
                var ids = Utils.getIds(rows);
                UserService.Delete(ids, function (data) {
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
*/
    }
    function tt($scope)
    {
        $scope.test = function()
        {
            console.log("AaA");
        }
    }

})();

