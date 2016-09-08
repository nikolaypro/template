(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController)
        .controller('tt', tt);

    UsersController.$inject = ['UserService', 'NgTableParams', '$scope', '$timeout', '$log', 'ALL_APP_ROLES', 'Utils', '$window', 'ngDialog', 'LocMsg'];
    function UsersController(UserService, NgTableParams, $scope, $timeout, $log, ALL_APP_ROLES, Utils, $window, ngDialog, LocMsg) {
        var vm = this;
        vm.roles = ALL_APP_ROLES;
        Utils.refreshEditRemoveButtonEnabled(vm);
// Modal dialog logic
        vm.showUserDialogFlag = false;
        vm.showUserDialog = function(isNew) {
            vm.userEditForm.$setPristine();
            vm.userEditForm.$setUntouched();
            vm.showEditPassword = isNew;
            vm.errorPasswordNotEqueals = false;
            vm.errorNotSelectedRole = false;
            vm.errorMessage = "";
            vm.showUserDialogFlag = false;
            vm.disableUserForm = false;
            if (isNew) {
                vm.user = null;
                vm.title = LocMsg.get('user.table.edit.title.new');
            } else {
                vm.user = angular.copy(Utils.getCheckedTableRow(vm.tableParams));
                vm.title = LocMsg.get('user.table.edit.title.edit');
            }
            $log.info('Title: ' + vm.title);
            $timeout(function() {
                vm.showUserDialogFlag = true;
                $scope.$digest();
            }, 0);
        };
        vm.getTitle = function() {
            return "A";
        };
        vm.submitUser = function() {
            $log.info('full name: ' + vm.user.fullName);
            $log.info('login: ' + vm.user.login);
            $log.info('psw: ' + vm.user.password);
            $log.info('repeat psw: ' + vm.user.repeatPassword);
            $log.info('roles: ' + vm.user.roles);
            if (vm.checkInputFields()) {
                var userCopy = angular.copy(vm.user);
                if (userCopy.password) {
                    userCopy.password = UserService.Base64.encode(userCopy.password);
                }
                vm.disableUserForm = true;
                UserService.Update(userCopy, function (data) {
                    if (data.data.success) {
                        $log.info("Success");
                        vm.showUserDialogFlag = false;
                        vm.tableParams.reload()
                    } else {
                        $log.info("Failed");
                        vm.errorMessage = data.data.message;
                    }
                    vm.disableUserForm = false;
                });
            }
        };
        vm.checkInputFields = function() {
            vm.errorPasswordNotEqueals = vm.showEditPassword && vm.user.password != vm.user.repeatPassword;
            vm.onRolesChange();
            return !vm.errorPasswordNotEqueals && !vm.errorNotSelectedRole
        };

        vm.onRolesChange = function() {
            $timeout(function() {
                $scope.$digest();
                 vm.errorNotSelectedRole = !(vm.user.roles && vm.user.roles.length > 0);
            }, 0);
        };

        vm.onRowChecked = function() {
            Utils.refreshEditRemoveButtonEnabled(vm, vm.tableParams);
        };
// End Modal dialog logic

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

/*
            Another way to open modal confirmation dialog
            ngDialog.openConfirm({ template: 'app/template/dialogs/yes-no-dialog.template.html', className: 'ngdialog-theme-default'}).then(function(value) {
                $log.info("selected OK: " + value);
            });
*/
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

        vm.formatRoles = function(roles) {
            var result = '';
            angular.forEach(roles, function(role) {
                if (result.length > 0) {
                    result += ", "
                }
                result += role;
            });
            return result;
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
                }});
    }
    function tt($scope)
    {
        $scope.test = function()
        {
            console.log("AaA");
        }
    }

})();

