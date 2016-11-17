(function () {
    'use strict';

    angular
        .module('app')
        .directive('datePicker', DatePicker);

    DatePicker.$inject = ['$log', '$timeout', 'Utils'];

    function DatePicker($log, $timeout, Utils) {
        return {
            templateUrl: 'app/template/datepicker/datepicker.html',
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
                vm.getStartWeek = function(date) {
                    if (date == undefined) {
                        return undefined;
                    }
                    var first = date.getDay() == 0 ? (date.getDate() - 6) : (date.getDate() - date.getDay() + 1); // First day is the day of the month - the day of the week

                    return new Date(new Date(date).setDate(first));
                };
                vm.getEndWeek = function(date) {
                    if (date == undefined) {
                        return undefined;
                    }
                    var startWeek = vm.getStartWeek(date);
                    var last = startWeek.getDate() + 6;
                    return new Date(new Date(startWeek).setDate(last));
                };
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
