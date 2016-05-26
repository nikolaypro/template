(function () {
    'use strict';

    angular
        .module('app')
        .controller('ReportsController', ReportsController);

    ReportsController.$inject = [];
    function ReportsController() {
        var vm = this;
        vm.tmpValue = 'REPORTS_TMP VALUE';
        vm.TEMPLATE_VALUE = 'REPORTS_TEMPLATE_VALUE_DELETE_ME';
    }

})();
