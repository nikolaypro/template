(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobSubTypeCostController', JobSubTypeCostController);

    JobSubTypeCostController.$inject = ['JobSubTypeCostService', '$log', 'TableUtils', '$scope', '$q', 'Utils'];
    function JobSubTypeCostController(JobSubTypeCostService, $log, TableUtils, $scope, $q, Utils) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'job-subtype-cost.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job-subtype-cost.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {id: 'asc'};
        params.defaultFilter = {};

        vm.loadFilterProductNames = function() {
            var deferred = $q.defer();
            JobSubTypeCostService.getProducts(function (data) {
                var res = [];
                angular.forEach(data, function (e) {
                    res.push(e.name);
                });
                deferred.resolve(res);
            });
            return deferred.promise;
        };
        vm.filterProduct = function(str, items) {
            return Utils.specialItemsFilter(str, items, function(item) {
                return item;
            });
        };

        TableUtils.initTablePage(vm, JobSubTypeCostService, $scope, params);
    }
})();

