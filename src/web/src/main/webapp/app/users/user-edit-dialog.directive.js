(function () {
    'use strict';

    angular
        .module('app')
        .directive('userEditDialog', UserEditDialog);

    UserEditDialog.$inject = ['UserService', '$timeout', '$log', 'Utils', 'LocMsg'];

    function UserEditDialog(UserService, $timeout, $log, Utils, LocMsg) {
        return {
            templateUrl: 'app/users/user-edit-dialog.html',
            restrict: 'E',
//            transclude: true,
//            replace: true,
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;
                vm.showUserDialogFlag = false;
                vm.submitUser = function() {
//                    $log.info('full name: ' + vm.user.fullName);
//                    $log.info('login: ' + vm.user.login);
//                    $log.info('psw: ' + vm.user.password);
//                    $log.info('repeat psw: ' + vm.user.repeatPassword);
//                    $log.info('roles: ' + vm.user.roles);
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
                vm.submit = vm.submitUser;// For Modal template
                vm.showUserDialog = function(user) {
                    var isNew = typeof user == 'undefined';
                    vm.userEditForm.$setPristine();
                    vm.userEditForm.$setUntouched();
                    vm.showEditPassword = isNew;
                    vm.errorPasswordNotEqueals = false;
                    vm.errorNotSelectedRole = false;
                    vm.errorMessage = "";
                    vm.showUserDialogFlag = false;
                    vm.disableUserForm = false;
                    if (isNew) {
                        vm.user = {};
                        vm.title = LocMsg.get('user.table.edit.title.new');
                    } else {
                        vm.user = angular.copy(user);
                        vm.title = LocMsg.get('user.table.edit.title.edit');
                        $log.info("Edit user with name: " + vm.user.fullName);
                    }
                    $log.info('Title: ' + vm.title);
                    vm.loadLocales(vm.user);
                    $timeout(function() {
                        vm.showUserDialogFlag = true;
//                        scope.$digest();
//                        scope.$parent.$digest();
                    }, 0);
                };
                vm.loadLocales = function(user) {
                    /*todo Кешировать рузультаты*/
                    UserService.loadLocales(function (data) {
                        $log.info("Success load locales");
                        vm.localeData = data.data;
                        if (!user.locale) {
                            user["locale"] = vm.localeData[0];
                            $log.info('Set locale: ' + user.locale);
                        }
                        $log.info('User locale: ' + user.locale);
                        vm.localeDataLoaded = true;
                    });
                };
                vm.checkInputFields = function() {
                    vm.errorPasswordNotEqueals = vm.showEditPassword && vm.user.password != vm.user.repeatPassword;
                    vm.onRolesChange();
                    return !vm.errorPasswordNotEqueals && !vm.errorNotSelectedRole
                };
                vm.onRolesChange = function() {
                    $timeout(function() {
                        scope.$digest();
                        vm.errorNotSelectedRole = !(vm.user.roles && vm.user.roles.length > 0);
                    }, 0);
                };
                vm.onModalClose = function() {
                    $log.info("ON CLOSE");
                    vm.tableParams.reload();
                };
            }
        }
    }
})();
