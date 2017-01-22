(function () {
    'use strict';

    angular
        .module('app-report')
        .controller('SalaryWithSubtypeShowReportController', SalaryWithSubtypeShowReportController);

    SalaryWithSubtypeShowReportController.$inject = ['LocMsg', '$rootScope', 'CommonUtils', '$log', '$filter'];
    function SalaryWithSubtypeShowReportController(LocMsg, $rootScope, CommonUtils, $log, $filter) {
        $log.info('Utils = ' + CommonUtils);
        var vm = this;
        vm.data = data;
        vm.dateFrom = $filter('date')(CommonUtils.getStartWeek(vm.data.date), CommonUtils.getDateFormat());
        vm.dateTo = $filter('date')(CommonUtils.getEndWeek(vm.data.date), CommonUtils.getDateFormat());
    }

})();
