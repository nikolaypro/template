(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

    UsersController.$inject = ['UrlService', '$http', 'UserService', 'NgTableParams'];
    function UsersController(UrlService, $http, UserService, NgTableParams) {
        var vm = this;
/*
        vm.doPost = function() {
            var requestParam = {
                page: 1,
                count: 10,
                orderBy: {name: 'asc'},
                isOrderAsc: true
            };
            return $http.post(UrlService.url('api/users'), requestParam).then(function(data) {

            });
        };
*/
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
