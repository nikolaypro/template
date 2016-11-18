(function () {
    'use strict';

    angular
        .module('app')
        .directive('datePicker', DatePicker);

    DatePicker.$inject = ['$log', '$timeout', 'Utils'];

    function DatePicker($log, $timeout, Utils) {
        return {
            templateUrl: 'app/template/weekpicker/weekpicker.html',
            restrict: 'E',
            transclude: true,
            // replace: true,
            scope: {
                // vm: '=',
                ngModel: '='
            },
            // require:"ngModel",
            link: function(scope, element, attrs, controller) {
                scope.vm = {};
                var vm = scope.vm;
                vm.dateFormat = Utils.getDateFormat();
                // vm.modelValue = controller.$modelValue;
                vm.getStartWeek = Utils.getStartWeek;
                vm.getEndWeek = Utils.getEndWeek;
                vm.datePeriodFilter = scope.ngModel;
                vm.dateOptions = {
                    formatYear: 'yy',
                    startingDay: 1
                };
/*
                Utils.getCurrDateWOTime();
                scope.ngModel = vm.datePeriodFilter;
*/

            }
        }
    }
})();
