(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

    UsersController.$inject = ['UserService', 'NgTableParams', '$scope', '$timeout', '$log'];
    function UsersController(UserService, NgTableParams, $scope, $timeout, $log) {
        var vm = this;
// Modal dialog logic
        vm.showNew = false;
        vm.showUserDialog = function(isNew) {
            vm.showNew = false;
            $timeout(function() {
                vm.userEditForm.$setPristine();
                vm.userEditForm.$setUntouched();
                vm.showEditPassword = isNew;
                vm.errorPasswordNotEqueals = false;
                vm.showNew = true;
                $scope.$digest();
            }, 0);
        };
        vm.submitUser = function() {
            $log.info('full name: ' + vm.user.fullName);
            $log.info('login: ' + vm.user.login);
            $log.info('psw: ' + vm.user.password);
            $log.info('repeat psw: ' + vm.user.repeatPassword);
            vm.errorPasswordNotEqueals = vm.showEditPassword && vm.user.password != vm.user.repeatPassword;
            if (!vm.errorPasswordNotEqueals) {

                //POST
            }
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
