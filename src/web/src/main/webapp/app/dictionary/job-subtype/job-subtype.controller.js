(function () {
    'use strict';

    angular
        .module('app')
        .controller('JobSubTypeController', JobSubTypeController);

    JobSubTypeController.$inject = ['JobSubTypeService', '$log', 'TableUtils', '$scope', '$q', '$filter'];
    function JobSubTypeController(JobSubTypeService, $log, TableUtils, $scope, $q, $filter) {
        var vm = this;
        vm.useInSalaryReportFilterData = [
            {
                id: true,
                title: $filter('i18n')("job-subtype.table.filter.use-in-salary-report")},
            {
                id: false,
                title: $filter('i18n')("job-subtype.table.filter.not-use-in-salary-report")}
            ];
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

