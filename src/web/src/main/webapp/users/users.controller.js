(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

    UsersController.$inject = ['UserService', 'NgTableParams', '$scope', '$timeout'];
    function UsersController(UserService, NgTableParams, $scope, $timeout) {
        var vm = this;
// Modal dialog logic
        vm.showNew = false;
        vm.doNew = function() {
            vm.showNew = false;
            $timeout(function() {
                vm.userEditForm.$setPristine();
                vm.userEditForm.$setUntouched();
                vm.showNew = true;
                $scope.$digest();
            }, 0);
        };
        vm.submitUser = function() {

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
