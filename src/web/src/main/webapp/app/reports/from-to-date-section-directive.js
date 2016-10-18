(function () {
    'use strict';

    angular
        .module('app')
        .directive('fromToDateReportSection', FromToDateReportSection);

    FromToDateReportSection.$inject = [];

    function FromToDateReportSection() {
        return {
            templateUrl: 'app/reports/from-to-date-section.html',
            restrict: 'E',
//            transclude: true,
//            replace: true,
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;
                vm.dateFrom = new Date();
                vm.dateTo = new Date();
            }
        }
    }
})();
