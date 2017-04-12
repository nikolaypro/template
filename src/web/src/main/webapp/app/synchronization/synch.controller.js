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
        vm.progressBarAction = function(progressId, onFinishCallback) {
            SynchronizationService.synch(progressId, function() {
                onFinishCallback();
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
