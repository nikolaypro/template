(function () {
    'use strict';

    angular
        .module('app')
        .factory('Utils', Utils);

    Utils.$inject = ['$log'];
    function Utils($log) {
        var service = {};
        service.refreshEditRemoveButtonEnabled = refreshEditRemoveButtonEnabled;
        service.getCheckedTableRow = getCheckedTableRow;
        return service;

        function refreshEditRemoveButtonEnabled(vm, tableParams) {
            var count = 0;
            if (tableParams) {
                angular.forEach(tableParams.data, function (row) {
                    if (row.row_checked) {
                        count++;
                    }
                });
            }
            vm.editDisabled = count == 0 || count > 1;
            vm.removeDisabled = count == 0;
        }

        function getCheckedTableRow(tableParams) {
            var result;
            if (tableParams) {
                angular.forEach(tableParams.data, function (row) {
                    if (row.row_checked) {
                        result = row;
                    }
                });
            }
            return result;
        }
    }

})();