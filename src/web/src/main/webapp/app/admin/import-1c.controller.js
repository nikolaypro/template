(function () {
    'use strict';

    angular
        .module('app')
        .controller('Import1cController', Import1cController);

    Import1cController.$inject = ['Import1cService', '$log', '$scope', '$q'];
    function Import1cController(Import1cService, $log, $scope, $q) {
        var vm = this;
        vm.checkImport = function() {
            Import1cService.checkImport(function(data) {
                vm.showImport = data.enableImport;
                vm.importLog = data.log;
            });
        };
        vm.doImport = function() {
            Import1cService.doImport(function(data) {
                vm.showImport = false;
                vm.importLog = data[0];
            });
        }
    }
})();

