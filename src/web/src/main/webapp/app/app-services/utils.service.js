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
        service.getCheckedTableRows = getCheckedTableRows;
        service.getIds = getIds;
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

        function getCheckedTableRows(tableParams) {
            var result = [];
            if (tableParams) {
                angular.forEach(tableParams.data, function (row) {
                    if (row.row_checked) {
                        result.push(row);
                    }
                });
            }
            return result;
        }

        function getIds(data) {
            var result = [];
            if (data) {
                angular.forEach(data, function (line) {
                    result.push(line.id);
                });
            }
            return result;
        }
    }

})();