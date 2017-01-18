(function () {
    'use strict';

    angular
        .module('app')
        .directive('progressBar', ProgressBar);

    ProgressBar.$inject = ['$log', '$timeout', '$filter', '$q', '$interval', 'ProgressService'];

    function ProgressBar($log, $timeout, $filter, $q, $interval, ProgressService) {
        return {
            templateUrl: 'app/template/progress/progress.html',
            restrict: 'E',
            transclude: true,
//             replace: true,
            scope: {
                percent: '=',
                state: '=',
                started: '=',
                action: '='
            },
            // require:"ngModel",
            link: function(scope, element, attrs, controller) {
                if (scope.percent == undefined) {
                    scope.percent = 0;
                }
                if (scope.state == undefined) {
                    scope.state = "";
                }
                var progressTimer;
                var startProgressTimer = function() {
                    $log.info("start timer called");
                    // Don't start a new fight if we are already fighting
                    if (angular.isDefined(progressTimer) ) return;

                    progressTimer = $interval(function() {
                        ProgressService.get(scope.progressId, function(data) {
                            scope.state = data.state;
                            scope.percent = data.percent;
                        });
                    }, 1000);
                };

                var stopProgressTimer = function() {
                    $log.info("stop timer called");
                    if (angular.isDefined(progressTimer)) {
                        $interval.cancel(progressTimer);
                        progressTimer = undefined;
                    }
                    scope.started = false;
                    scope.percent = 0;
                    scope.state = "";
                };

                /*
                 var resetProgressTimer = function() {
                 vm.importState = "";
                 vm.importPercent = "";
                 };
                 */

                scope.$on('$destroy', function() {
                    // Make sure that the interval is destroyed too
                    stopProgressTimer();
                });

                scope.$watch('started', function(newVal, oldVal){
                    $log.info("Started: " + newVal);
                    if (newVal) {
                        // start timer
                        ProgressService.start(function(progressId) {
                            scope.progressId = progressId;
                            scope.action(progressId, function() {
                                stopProgressTimer();
                            });
                            startProgressTimer();
                        });
                    } else {
                        // stop timer
                    }
                });

            }
        }
    }
})();
