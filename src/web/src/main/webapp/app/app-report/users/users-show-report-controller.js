(function () {
    'use strict';

    angular
        .module('app-report')
        .controller('UsersShowReportController', UsersShowReportController);

    UsersShowReportController.$inject = ['LocMsg', '$rootScope'];
    function UsersShowReportController(LocMsg, $rootScope) {
        var vm = this;
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
        vm.data = data;
    }

})();
