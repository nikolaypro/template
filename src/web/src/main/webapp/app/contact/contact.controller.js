(function () {
    'use strict';

    angular
        .module('app')
        .controller('ContactController', ContactController);

    ContactController.$inject = ['$rootScope', '$scope'];
    function ContactController($rootScope, $scope) {
        var vm = this;
        vm.tmpValue = 'CONTACT TMP VALUE';
        vm.TEMPLATE_VALUE = 'TEMPLATE_VALUE_DELETE_ME';
    }

})();
