(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['UserService', '$rootScope', '$location', 'AuthenticationService'];
    function HomeController(UserService, $rootScope, $location, AuthenticationService) {
        var vm = this;

        vm.user = null;
        vm.allUsers = [];
        vm.deleteUser = deleteUser;
        vm.deleteUser = deleteUser;
        vm.logout = logout;

        initController();

        function initController() {
            loadCurrentUser();
            loadAllUsers();
        }

        function loadCurrentUser() {
            UserService.getByUsername($rootScope.globals.currentUser.username)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllUsers() {
            UserService.getAll()
                .then(function (users) {
                    vm.allUsers = users;
                });
        }

        function deleteUser(id) {
            UserService.delete(id)
            .then(function () {
                loadAllUsers();
            });
        }

        function logout() {
            vm.dataLoading = true;
            AuthenticationService.Logout(function(success) {
                AuthenticationService.ClearCredentials();
                vm.dataLoading = false;
                $location.path('#/login');
            });
        }

    }

})();