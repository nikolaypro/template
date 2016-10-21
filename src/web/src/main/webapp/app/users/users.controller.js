(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

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
            loadFromServerForEdit: false,
            defaultSort: {fullName: 'asc'}
        };
        TableUtils.initTablePage(vm, UserService, $scope, params);

    }

})();

