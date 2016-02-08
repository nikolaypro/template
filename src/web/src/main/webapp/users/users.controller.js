(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

    UsersController.$inject = [];
    function UsersController() {
        var vm = this;
        vm.tmpValue = 'USERS_TMP VALUE';
        vm.TEMPLATE_VALUE = 'USERS_TEMPLATE_VALUE_DELETE_ME';
    }

})();
