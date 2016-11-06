(function () {
    'use strict';

    angular
        .module('app')
        .directive('userEditDialog', UserEditDialog);

    UserEditDialog.$inject = ['UserService', '$timeout', '$log', 'Utils', 'LocMsg', 'EditDialogUtils'];

    function UserEditDialog(UserService, $timeout, $log, Utils, LocMsg, EditDialogUtils) {
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

                vm.onRolesChange = function() {
                    $timeout(function() {
                        scope.$digest();
                        vm.errorNotSelectedRole = !(vm.user.roles && vm.user.roles.length > 0);
                    }, 0);
                };

                var loadLocales = function(user) {
                    /*todo ���������� ����������*/
                    UserService.loadLocales(function (data) {
//                        $log.info("Success load locales");
                        vm.localeData = data;
                        if (!user.locale) {
                            user["locale"] = vm.localeData[0];
//                            $log.info('Set locale: ' + user.locale);
                        }
//                        $log.info('User locale: ' + user.locale);
                        vm.localeDataLoaded = true;
                    });
                };

                // Configure show modal
                var showModalParams = {};
                showModalParams.entityName = 'user';
                showModalParams.titleNew = 'user.table.edit.title.new';
                showModalParams.titleEdit = 'user.table.edit.title.edit';
                showModalParams.onShow = function(isNew) {
                    vm.showEditPassword = isNew;
                    vm.errorPasswordNotEqueals = false;
                    vm.errorNotSelectedRole = false;
                    loadLocales(vm.user);

                };

                // Configure submit
                var submitParams = {};
                submitParams.submit = UserService.update;
                submitParams.getEntity = function() {
                    var userCopy = angular.copy(vm.user);
                    if (userCopy.password) {
                        userCopy.password = UserService.base64.encode(userCopy.password);
                    }
                    return userCopy;
                };
                submitParams.checkInputFields = function() {
                    vm.isShowRequired(vm.editForm.fullName);
                    vm.isShowRequired(vm.editForm.login);
                    vm.isShowRequired(vm.editForm.password);
                    vm.isShowRequired(vm.editForm.repeatPassword);
                    vm.isShowRequired(vm.editForm.repeatPassword);
                    vm.errorPasswordNotEqueals = vm.showEditPassword && vm.user.password != vm.user.repeatPassword;
                    vm.onRolesChange();
                    return !vm.errorPasswordNotEqueals && !vm.errorNotSelectedRole;
                };

                EditDialogUtils.initEditDialog(vm, showModalParams, submitParams);

            }
        }
    }
})();
