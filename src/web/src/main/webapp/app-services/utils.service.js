(function () {
    'use strict';

    angular
        .module('app')
        .factory('Utils', Utils);

    FlashService.$inject = ['$log'];
    function Utils($log) {
        var service = {};
        return service;

        function tmp(form) {
            $log.info('VALUES ***********************');
            angular.forEach(form, function(value, key) {
                if (typeof value === 'object' && (value.hasOwnProperty('$modelValue'))) {
                    $log.info(value.$name);
                }
            });
            $log.info('***********************');
        }
    }

})();