(function () {
    'use strict';

    angular
        .module('app')
        .directive('mainTopMenu', MainTopMenu);

    MainTopMenu.$inject = ['$rootScope', '$location', 'ALL_APP_ROLES'];

    function MainTopMenu($rootScope, $location, ALL_APP_ROLES) {
        var getRoleMenu = function(role) {
            switch (role) {
                case ALL_APP_ROLES.admin:
                        return ['users', 'reports'];
                case ALL_APP_ROLES.regular:
                    return ['contact', 'dropdown', 'action', 'separated_link'];
                default:
                    alert('Unknown role: "' + role + '"');
                    return [];
            }
        };
        var getEnabledMenu = function() {
            var roles = [];
//            $rootScope.globals.currentUser.roles.forEach(function(item, i, arr) {
            $.each($rootScope.globals.currentUser.roles, function(i, el) {
                Array.prototype.push.apply(roles, getRoleMenu(el));
            });
            var result = [];
            $.each(roles, function(i, el) {
                if($.inArray(el, result) === -1) result.push(el);
            });
            return result;
        };

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
                if (result.length > 0) {
                    result += ", "
                }
                result += role;
            });
            return result;
        };

        return {
            restrict: 'E',
            templateUrl: 'app/template/maintopmenu/mainTopMenu.html',
            controller: ["$scope", "AuthenticationService", "$location", function ($scope, AuthenticationService, $location) {
                $scope.$ = $;
//                $scope.enabledMenu = ['home', 'about', 'contact', 'dropdown', 'action', 'anotherAction', 'somethingElseHere', 'separatedLink', 'oneMoreSeparatedLink']
                $scope.enabledMenu = getEnabledMenu();
                $scope.userName = $rootScope.globals.currentUser.username;
                $scope.userRoles = formatRoles($rootScope.globals.currentUser.roles);
                var vm = $scope;
//                $('[data-toggle="tooltip"]').tooltip(); Use for Bootstrap simple tooltip
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
