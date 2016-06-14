(function () {
    'use strict';

    angular
        .module('app')
        .directive('userEditDialog', UserEditDialog);

    UserEditDialog.$inject = [];

    function UserEditDialog() {
        return {
            templateUrl: 'app/users/user-edit-dialog.html',
            restrict: 'E',
            transclude: true,
            replace: true,
            scope: true
        }
    }
})();
