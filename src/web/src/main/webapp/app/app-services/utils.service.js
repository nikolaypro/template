(function () {
    'use strict';

    angular
        .module('app')
        .factory('Utils', Utils);

    Utils.$inject = ['$log', 'ALL_APP_ROLES', '$rootScope'];
    function Utils($log, ALL_APP_ROLES, $rootScope) {
        var service = {};
        service.refreshEditRemoveButtonEnabled = refreshEditRemoveButtonEnabled;
        service.getCheckedTableRow = getCheckedTableRow;
        service.getCheckedTableRows = getCheckedTableRows;
        service.getIds = getIds;
        service.showConfirm = showConfirm;
        service.showWarning = showWarning;
        service.getEnabledMenu = getEnabledMenu;
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

        function showConfirm(title, message, okHandler) {
            return BootstrapDialog.show({
                title: title,
                type: BootstrapDialog.DEFAULTS,
                message: message,
                closable: true,
                closeByBackdrop: false,
                closeByKeyboard: true,
                draggable: true,
                buttons: [{
                    label: 'Ok',
                    cssClass: 'btn-primary',
//                    icon: 'glyphicon glyphicon-send',
                    autospin: false,
                    action: function(dialogRef){
                        $log.info("toggled Ok");
                        dialogRef.enableButtons(false);
                        dialogRef.setClosable(false);

//                        dialogRef.close();
                        okHandler(dialogRef);
                    }
                }, {
                    label: 'Cancel',
                    cssClass: 'btn-primary',
                    action: function(dialogRef){
                        $log.info("toggled Cancel");
                        dialogRef.close();
                    }
                }
                ]
            });
        }

        function showWarning(message) {
            return BootstrapDialog.show({
                type: BootstrapDialog.TYPE_WARNING,
                message: message,
                closable: true,
                closeByBackdrop: true,
                closeByKeyboard: true,
                draggable: true,
                buttons: [{
                    label: 'Close',
                    cssClass: 'btn-primary'
                }]
            });
        }

        function getRoleMenu (role) {
            switch (role) {
                case ALL_APP_ROLES.admin:
                    return ['users', 'reports'];
                case ALL_APP_ROLES.regular:
                    return ['contact', 'dropdown', 'action', 'separated_link'];
                default:
                    alert('Unknown role: "' + role + '"');
                    return [];
            }
        }
        function getEnabledMenu () {
            var roles = [];
            $.each($rootScope.globals.currentUser.roles, function(i, el) {
                Array.prototype.push.apply(roles, getRoleMenu(el));
            });
            var result = [];
            $.each(roles, function(i, el) {
                if($.inArray(el, result) === -1) result.push(el);
            });
            return result;
        }

    }

})();