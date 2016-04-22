(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

    UsersController.$inject = ['UrlService', '$http', 'UserService', 'NgTableParams', '$scope', '$timeout'];
    function UsersController(UrlService, $http, UserService, NgTableParams, $scope, $timeout) {
        var vm = this;
        vm.showNew = false;
        vm.doNew = function() {
            vm.showNew = false;/*!vm.showNew;*/
            $timeout(function() {
                vm.showNew = true;
                $scope.$digest();
            }, 0);


        };
        vm.tmpValue = 'USERS_TMP VALUE';
        vm.TEMPLATE_VALUE = 'USERS_TEMPLATE_VALUE_DELETE_ME';
        vm.tableParams = new NgTableParams(
            {
                page: 1,
                count: 10,
                sorting: {fullName: 'asc'}
            },
            {
                total: 0,
                getData: function ($defer, params) {
                    /* code to fetch data that matches the params values EG: */
                    UserService.GetAll(params, function (data) {

//                        params.total(data.inlineCount);
                        params.total(data.data.total);
                        $defer.resolve(data.data.list)
                    });
                }});
    }

})();
