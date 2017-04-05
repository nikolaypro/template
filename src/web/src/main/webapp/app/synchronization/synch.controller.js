(function () {
    'use strict';

    angular
        .module('app')
        .controller('SynchronizationController', SynchronizationController);

    SynchronizationController.$inject = ['LocMsg', '$log', 'Utils', '$rootScope', 'SynchronizationService'];
    function SynchronizationController(LocMsg, $log, Utils, $rootScope, SynchronizationService) {
        var vm = this;
        vm.synchEnabled = true;
        vm.synch = function () {
            vm.synchEnabled = false;
            SynchronizationService.synch(function() {
                vm.synchEnabled = true;
            });
        }
    }

})();
