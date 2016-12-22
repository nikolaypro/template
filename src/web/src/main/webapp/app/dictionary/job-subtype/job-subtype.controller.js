(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobSubTypeController', JobSubTypeController);

    JobSubTypeController.$inject = ['JobSubTypeService', '$log', 'TableUtils', '$scope', '$q'];
    function JobSubTypeController(JobSubTypeService, $log, TableUtils, $scope, $q) {
        var vm = this;
        var params = {};
        params.deleteConfirmManyMsg = 'job-subtype.table.delete.confirm.many';
        params.deleteConfirmMsg = 'job-subtype.table.delete.confirm';
        params.loadFromServerForEdit = true;
        params.defaultSort = {name: 'asc'};
        params.defaultFilter = {};
//        vm.loadFilterJobTypeNames = ["name1", "name2"];
//        var filterJobTypeNames = null;
        vm.loadFilterJobTypeNames = function() {
//            if (filterJobTypeNames == null) {
                var deferred = $q.defer();
                JobSubTypeService.getJobTypes(function (data) {
                    var res = [];
                    angular.forEach(data, function (e) {
                        res.push(e.name);
                    });
//                    filterJobTypeNames = res;
                    deferred.resolve(res);
                });
                return deferred.promise;
//            }
//            return filterJobTypeNames;
        };
//        vm.loadFilterJobTypes = JobSubTypeService.getJobTypes;

        TableUtils.initTablePage(vm, JobSubTypeService, $scope, params);
    }
})();

