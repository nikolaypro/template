(function () {
    'use strict';

    angular
        .module('app')
        .factory('Utils', Utils);

    Utils.$inject = ['$log', 'ALL_APP_ROLES', '$rootScope', 'LocMsg', 'uibDateParser', 'CommonUtils', '$injector', '$q'];
    function Utils($log, ALL_APP_ROLES, $rootScope, LocMsg, uibDateParser, CommonUtils, $injector, $q) {
        var service = {};
        service.refreshEditRemoveButtonEnabled = refreshEditRemoveButtonEnabled;
        service.getCheckedTableRow = getCheckedTableRow;
        service.getCheckedTableRows = getCheckedTableRows;
        service.getIds = getIds;
        service.showConfirm = showConfirm;
        service.showWarning = showWarning;
        service.getEnabledMenu = getEnabledMenu;
        service.isCurrentAdminRole = isCurrentAdminRole;
        service.handleSuccess = handleSuccess;
        service.handleError = handleError;
        service.callCheckBeforeInvokeService = callCheckBeforeInvokeService;
        service.parseDate = parseDate;
        service.isValidDate = isValidDate;
        service.getDateFormat = CommonUtils.getDateFormat;
        service.getCurrDateWOTime = CommonUtils.getCurrDateWOTime;
        service.getStartWeek = CommonUtils.getStartWeek;
        service.getEndWeek = CommonUtils.getEndWeek;
        service.updateDocumentTitle = updateDocumentTitle;
        service.specialItemsFilter = specialItemsFilter;
        service.specialItemsFilterWithSequence = specialItemsFilterWithSequence;
        service.doAutocompleteProductFilter = doAutocompleteProductFilter;// for

// FOR FILTER COMPONENTS
        service.productFilter = {
            loadFilterProductNames: loadFilterProductNames,
            doFilterProduct: doFilterProduct
        };
// ----------------------
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

        function showConfirm(title, message, okHandler, cancelHandler) {
            return BootstrapDialog.show({
                title: title,
                type: BootstrapDialog.DEFAULTS,
                message: message,
                closable: true,
                closeByBackdrop: false,
                closeByKeyboard: true,
                draggable: true,
                buttons: [{
                    label: LocMsg.get('common.yes'),
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
                    label: LocMsg.get('common.cancel'),
                    cssClass: 'btn-primary',
                    action: function(dialogRef){
                        $log.info("toggled Cancel");
                        dialogRef.close();
                        if (cancelHandler) {
                            cancelHandler(dialogRef);
                        }
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
                    cssClass: 'btn-primary',
                    action: function(dialogRef){
                        dialogRef.close();
                    }
                }]
            });
        }

        function getRoleMenu(role) {
            switch (role) {
                case ALL_APP_ROLES.admin:
                    return [
                        'orders',
                        'new_order',
                        'new_product',
                        'new_cloth',
                        'home'
                    ];
                case ALL_APP_ROLES.regular:
                    return [
                        'orders',
                        'new_order',
                        'new_product',
                        'new_cloth',
                        'home'
                    ];
                default:
                    alert('Unknown role: "' + role + '"');
                    return [];
            }
        }

        function isCurrentAdminRole() {
            return $rootScope.globals.currentUser != undefined && $rootScope.globals.currentUser.roles.indexOf(ALL_APP_ROLES.admin) != -1;
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

        function handleSuccess(callback) {
            return function(data) {
                if (typeof data.data == 'undefined') {
                    data.data['success'] = true;
                }
                callback(data.data);
            }
        }

        function handleError(response) {
//            FlashService.Error(response.data.error);
            return function () {
                return { success: false, message: response.data.error};
            };
        }

        /**
         * Use in case for select a function to invoke by result of check function and user answer.
         * All functionas must has the same params with callback function as last parameter with one parameter "data":
         *
         * function(...., callbackFun) {
         *      ....
         *      callbackFun(data);
         * }
         * @param checkFn
         * @param trueFn - will use if checkFn result true. "Data" params in callbackFun must contains "result" boolean field
         * @param falseFn - will use if checkFn return false and user say "Yes"
         * @param params - params for all functions
         * @param callback - invoke this function if was invoke trueFn or falseFn
         * @param questionMessage - will use for show to user when checkFn return false
         */
        function callCheckBeforeInvokeService(checkFn, trueFn, falseFn, params, callback, questionMessage) {
            var checkParams = angular.copy(params);
            checkParams.push(function(data) {
                var callbackParams = angular.copy(params);
                callbackParams.push(callback);
                if (data.result) {
                    trueFn.apply(this, callbackParams);
                } else {
                    showConfirm(LocMsg.get('common.warning'), LocMsg.get(questionMessage), function(dialogRef) {
                        falseFn.apply(this, callbackParams);
                        dialogRef.close();
                    },
                    function(dialogRef) {
                        callback({success: false});
                        dialogRef.$modal.scope().$digest();
                    });
                }
            });

            checkFn.apply(this, (checkParams));
        }

        function parseDate(dateStr) {
            return uibDateParser.parse(dateStr, CommonUtils.getDateFormat());
        }

        function isValidDate(scope, date) {
//                    el.$$parentForm.$submitted
            return !scope.submitPressed || parseDate(date) != undefined;
        }

        function updateDocumentTitle() {
            document.title = LocMsg.get('common.document.title');
            if ($rootScope.globals.appVersion != undefined) {
                document.title += (" ver: " + $rootScope.globals.appVersion);
            }
        }

        /**
         * Filter word sequence must be equals item word sequence. For example:
         * search str: "on th" will not find a string "One Two Three Four Five"
         * search str: "on tw th" will find a string "One Two Three Four Five"
         * @param str
         * @param items
         * @param nameProvider
         * @returns {*}
         */
        function specialItemsFilterWithSequence(str, items, nameProvider) {
            if (typeof str == 'undefined') {
                return items;
            }

            if (nameProvider == undefined) {
                nameProvider = function(item) {
                    return item.name;
                }
            }

            var getArray = function(str) {
                return str.trim().replace('  ', ' ').split(' ');
            };

            var strArray = getArray(str);
            var result = [];
            angular.forEach(items, function(item) {
                var itemArray = getArray(nameProvider(item));
                if (strArray.length > itemArray.length) {
                    return;
                }
                for (var strIndex = 0; strIndex < strArray.length; strIndex++) {
                    var strItem = strArray[strIndex];
                    var itemElement = itemArray[strIndex];
                    if ((itemElement).toLowerCase().indexOf(('' + strItem).toLowerCase()) != 0) {
                        return;
                    }
                }
                result.push(item);
            });
            return result;
        }


        /**
         * Words can be absent. For example:
         * search str: "on th" will be find a string "One Two Three Four Five"
         * @param str
         * @param items
         * @param nameProvider
         * @returns {*}
         */
        function specialItemsFilter(str, items, nameProvider) {
            if (typeof str == 'undefined') {
                return items;
            }

            if (nameProvider == undefined) {
                nameProvider = function(item) {
                    return item.name;
                }
            }

            var getArray = function(str) {
                return str.trim().replace('  ', ' ').split(' ');
            };

            var strArray = getArray(str);
            var result = [];
            angular.forEach(items, function(item) {
                var itemArray = getArray(nameProvider(item));
                var wordIndex = 0;
                var matched;
                for (var strIndex = 0; strIndex < strArray.length; strIndex++) {
                    var strItem = strArray[strIndex];
                    matched = false;
                    for (;wordIndex < itemArray.length; wordIndex++) {
                        var itemElement = itemArray[wordIndex];
                        if ((itemElement).toLowerCase().indexOf(('' + strItem).toLowerCase()) == 0) {
                            wordIndex++;
                            matched = true;
                            break;
                        }
                    }
                    if (!matched) {
                        break
                    }
                    $log.info('BREAK');
                }
                if (matched) {
                    result.push(item);
                }
            });
            return result;
        }

        function standardFilter(str, items, nameProvider) {
            var result = [];
            angular.forEach(items, function(item) {
                if (typeof str == 'undefined' || nameProvider(item).toLowerCase().indexOf(('' + str).toLowerCase()) > -1) {
                    result.push(item);
                }
            });
            // $log.info("Str = " + str + ", Filtered: " + result);
            return result;
        }

        function doAutocompleteProductFilter(str, items, nameProvider) {
            var type = $rootScope.globals.settings.productAutocompleteType;
            if (type == 'START_WORD_SEQUENCE') {
                $log.info("START_WORD_SEQUENCE autocomplete");
                return specialItemsFilter(str, items, nameProvider);
            }
            if (type == 'START_WORD_SEQUENCE_STRONG') {
                $log.info("START_WORD_SEQUENCE_STRONG autocomplete");
                return specialItemsFilterWithSequence(str, items, nameProvider);
            }
            $log.info("STANDARD autocomplete");
            return standardFilter(str, items, nameProvider);
        }

        function loadFilterProductNames() {
            var deferred = $q.defer();
            $injector.get('ProductService').getForFilter(function (data) {
                var res = [];
                angular.forEach(data, function (e) {
                    res.push(e.name);
                });
                deferred.resolve(res);
            });
            return deferred.promise;
        }

        function doFilterProduct(str, items) {
            return doAutocompleteProductFilter(str, items, function(item) {
                return item;
            });
        }


    }

})();