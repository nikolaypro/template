(function () {
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$location', 'AuthenticationService', 'FlashService', '$rootScope'];
    function LoginController($location, AuthenticationService, FlashService, $rootScope) {
        var vm = this;

        var logoutProcess = false;
        var autoLoginProcess = false;

        var updateDataLoading = function() {
            vm.dataLoading = logoutProcess || autoLoginProcess;
        };

        // --- ONLY FOR GURGEN ----
        // ------------------------


        vm.login = login;

        (function initController() {
            // reset login status
            if ($rootScope.globals && $rootScope.globals.currentUser) {
                logoutProcess = true;
                updateDataLoading();
                AuthenticationService.Logout(function (success) {
                    AuthenticationService.ClearCredentials();
                    logoutProcess = false;
                    updateDataLoading();
                });
            }
        })();

        autoLoginProcess = true;
        updateDataLoading();
        AuthenticationService.AutoLogin(function(response) {
            if (response.enabled) {
                vm.username = response.login;
                vm.password = response.password;
                login();
            }
            autoLoginProcess = false;
            updateDataLoading();
        });

        function login() {
            vm.dataLoading = true;
            AuthenticationService.Login(vm.username, vm.password, function (response) {
                if (response.success) {
                    AuthenticationService.SetCredentials(vm.username, vm.password, response.roles, response.locale, response.appVersion);
                    $location.path('/');
                } else {
                    FlashService.Error(response.message);
                    vm.dataLoading = false;
                }
            });
        }
    }

})();
