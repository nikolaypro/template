(function () {
    'use strict';
    angular
        .module('app')
        .factory('LongRunService', LongRunService);

    LongRunService.$inject = ['$rootScope', '$log'];

    function LongRunService($rootScope, $log) {
        var service = {};
        service.startLong = startLong;
        service.endLong = endLong;
        return service;

        function startLong() {
            $log.info("Start long");
            $rootScope.dataLoading = true;
        }

        function endLong() {
            $log.info("End long");
            $rootScope.dataLoading = false;
        }
    }
})();
