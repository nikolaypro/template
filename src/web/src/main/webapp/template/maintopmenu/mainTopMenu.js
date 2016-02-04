(function () {
    'use strict';

    angular
        .module('app')
        .directive('mainTopMenu', MainTopMenu);

    MainTopMenu.$inject = ['$rootScope'];

    function MainTopMenu($rootScope) {
        var getRoleMenu = function(role) {
            switch (role.name) {
                case 'ROLE_ADMIN':
                        return ['home', 'about'];
                case 'ROLE_REGULAR':
                    return ['contact', 'dropdown', 'action', 'separated_link'];
                default:
                    alert('Unknown role: "' + role.name + '"');
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
        return {
            restrict: 'E',
            templateUrl: 'template/maintopmenu/mainTopMenu.html',
            controller: ["$scope", "AuthenticationService", "$location", function ($scope, AuthenticationService, $location) {
                $scope.$ = $;
//                $scope.enabledMenu = ['home', 'about', 'contact', 'dropdown', 'action', 'anotherAction', 'somethingElseHere', 'separatedLink', 'oneMoreSeparatedLink']
                $scope.enabledMenu = getEnabledMenu();
                $scope.userName = $rootScope.globals.currentUser.username;
                var vm = $scope;
                $scope.logout = function() {
                    vm.dataLoading = true;
                    AuthenticationService.Logout(function(success) {
                        AuthenticationService.ClearCredentials();
                        vm.dataLoading = false;
                        $location.path('#/login');
                    });

                }
            }]

        }
    }
})();
