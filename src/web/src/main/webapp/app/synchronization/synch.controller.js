(function () {
    'use strict';

    angular
        .module('app')
        .controller('SynchronizationController', SynchronizationController);

    SynchronizationController.$inject = ['LocMsg', '$log', 'Utils', '$rootScope', 'SynchronizationService', '$scope'];
    function SynchronizationController(LocMsg, $log, Utils, $rootScope, SynchronizationService, $scope) {
        var vm = this;
        vm.synchStarted = false;
        vm.dateFormat = Utils.getDateFormat();
        vm.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        };
        vm.dateFilter = new Date();
/*
        vm.groups = [
            {
                lines: [
                    "line 1", "line 2", "line 3", "line 4"
                ]
            },
            {
                lines: [
                    "line 21", "line 22", "line 23", "line 24"
                ]
            },
            {
                lines: [
                    "line 31", "line 32", "line 33", "line 34"
                ]
            }
        ];
*/
/*
        SynchronizationService.getLog(vm.dateFilter, function(data) {
            vm.groups = data;
        });
*/
        vm.synch = function () {
            vm.synchStarted = true;
            SynchronizationService.synch(function() {
                reloadLog();
                vm.synchStarted = false;
            });
        };
        $scope.$watch('vm.dateFilter', function(newVal, oldVal){
            reloadLog();
        });

        function reloadLog() {
            SynchronizationService.getLog(vm.dateFilter, function(data) {
                vm.groups = data;
            });
        }

    }

})();
