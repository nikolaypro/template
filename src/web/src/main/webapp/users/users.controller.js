(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);

    UsersController.$inject = ['UserService', 'NgTableParams'];
    function UsersController(UserService, NgTableParams) {
        var vm = this;
        vm.tmpValue = 'USERS_TMP VALUE';
        vm.TEMPLATE_VALUE = 'USERS_TEMPLATE_VALUE_DELETE_ME';
        vm.tableParams = new NgTableParams(
            {
                page: 1,
                count: 10
                /*, sorting: {foo: 'asc'}*/
            },
            {
                total: 0,
                getData: function ($defer, params) {
                    /* code to fetch data that matches the params values EG: */
//                    var promise = $defer.promise;
                    UserService.GetAll(function (data) {

//                        params.total(data.inlineCount);
                        params.total(data.data.length);
//                        promise.resolve(data.data)
                        $defer.resolve(data.data)
//                        return data.data;
                    });
//                    return $defer;
                }});
    }

})();
