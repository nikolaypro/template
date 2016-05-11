 (function () {
 'use strict';
 angular
 .module('app')
 .factory('LongRunService', LongRunService);

 LongRunService.$inject = ['$rootScope'];

 function LongRunService($rootScope) {
     var service = {};
     service.startLong = startLong;
     service.endLong = endLong;
     return service;

     function startLong() {
        $rootScope.dataLoading = true;
     }

     function endLong() {
         $rootScope.dataLoading = false;
     }
 }
 })();
