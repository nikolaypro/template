(function () {
    'use strict';

    angular
        .module('app')
        .controller('MainController', MainController);

    MainController.$inject = [];
    function MainController() {
        var vm = this;
        vm.tmpValue = 'TMP VALUE';
        vm.TEMPLATE_VALUE = 'TEMPLATE_VALUE_DELETE_ME';
    }

})();
