(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

    UsersController.$inject = ['UserService', 'NgTableParams', '$scope', '$timeout', '$log', 'allAppRoles'];
    function UsersController(UserService, NgTableParams, $scope, $timeout, $log, allAppRoles) {
        var vm = this;
        vm.roles = allAppRoles;
// Modal dialog logic
        vm.showNew = false;
        vm.showUserDialog = function(isNew) {
            vm.showNew = false;
            $timeout(function() {
                vm.userEditForm.$setPristine();
                vm.userEditForm.$setUntouched();
                vm.showEditPassword = isNew;
                vm.errorPasswordNotEqueals = false;
                vm.errorNotSelectedRole = false;
                vm.showNew = true;
                $scope.$digest();
            }, 0);
        };
        vm.submitUser = function() {
            $log.info('full name: ' + vm.user.fullName);
            $log.info('login: ' + vm.user.login);
            $log.info('psw: ' + vm.user.password);
            $log.info('repeat psw: ' + vm.user.repeatPassword);
            $log.info('roles: ' + vm.user.roles);
            vm.errorPasswordNotEqueals = vm.showEditPassword && vm.user.password != vm.user.repeatPassword;
            vm.onRolesChange();
            if (!vm.errorPasswordNotEqueals && !vm.errorNotSelectedRole) {

                //POST
            }
        };

        vm.onRolesChange = function() {
            $timeout(function() {
                $scope.$digest();
                 vm.errorNotSelectedRole = !(vm.user.roles && vm.user.roles.length > 0);
            }, 0);
        };

        vm.doRemove = function() {
            $log.info('do remove');
        };
// End Modal dialog logic

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

})();
