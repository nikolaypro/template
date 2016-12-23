(function () {
    'use strict';

    angular
        .module('app')
        .controller('Import1cController', Import1cController);

    Import1cController.$inject = ['Import1cService', '$log', '$scope', '$q', '$interval'];
    function Import1cController(Import1cService, $log, $scope, $q, $interval) {
        var vm = this;

        var progressTimer;
        $scope.startProgressTimer = function() {
            // Don't start a new fight if we are already fighting
            if (angular.isDefined(progressTimer) ) return;

            progressTimer = $interval(function() {
                Import1cService.getProgress(function(data) {
                    vm.importState = data.state;
                    vm.importPercent = data.percent;
                });
            }, 500);
        };

        $scope.stopProgressTimer = function() {
            if (angular.isDefined(progressTimer)) {
                $interval.cancel(progressTimer);
                progressTimer = undefined;
            }
            vm.importState = "";
            vm.importPercent = "";
        };

/*
        $scope.resetProgressTimer = function() {
            vm.importState = "";
            vm.importPercent = "";
        };
*/

        $scope.$on('$destroy', function() {
            // Make sure that the interval is destroyed too
            $scope.stopProgressTimer();
        });

        vm.checkImport = function() {
            $scope.startProgressTimer();
            Import1cService.checkImport(function(data) {
                vm.showImport = data.enableImport;
                vm.importLog = data.log;
                $scope.stopProgressTimer();
            });
        };
        vm.doImport = function() {
            $scope.startProgressTimer();
            Import1cService.doImport(function(data) {
                vm.showImport = false;
                vm.importLog = data[0];
                $scope.stopProgressTimer();
            });
        }

    }
})();

