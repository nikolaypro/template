(function () {
    'use strict';

    angular
        .module('app')
        .directive('mainTopMenu', MainTopMenu);

    MainTopMenu.$inject = ['$rootScope', '$location', 'ALL_APP_ROLES', 'Utils', 'LocMsg'];

    function MainTopMenu($rootScope, $location, ALL_APP_ROLES, Utils, LocMsg) {

        var getSelectedMenu = function($location) {
            var patch = $location.$$path;
            if (patch) {
                var index = patch.indexOf('/', 1);
                if (index === -1) {
                    return patch.substring(1);
                }
                return patch.substring(1, index);
            }
            return '';
        };
        var formatRoles = function(roles) {
            var result = '';
            angular.forEach(roles, function(role) {
/*
                if (result.length > 0) {
                    result += ", "
                }
*/
                result += LocMsg.get(role);
                result += "\n"
            });
            return result;
        };

        return {
            restrict: 'E',
            templateUrl: 'app/template/maintopmenu/mainTopMenu.html',
            controller: ["$scope", "AuthenticationService", "$location", function ($scope, AuthenticationService, $location) {
                $scope.$ = $;
                $scope.enabledMenu = Utils.getEnabledMenu();
                $scope.userName = $rootScope.globals.currentUser.username;
                $scope.userRoles = formatRoles($rootScope.globals.currentUser.roles);
                var vm = $scope;
                $scope.logout = function() {
                    vm.dataLoading = true;
                    AuthenticationService.Logout(function(success) {
                        AuthenticationService.ClearCredentials();
                        vm.dataLoading = false;
                        $location.path('/login');
                    });
                };
                $scope.isActive = function(menuItem) {
                    return menuItem == getSelectedMenu($location);
                };
            }]

        }
    }
})();
