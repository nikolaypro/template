(function () {
    'use strict';

    angular
        .module('app')
        .directive('typeAhead', TypeAhead);

    TypeAhead.$inject = ['$log', '$timeout'];

    function TypeAhead($log, $timeout) {
        return {
            templateUrl: 'app/template/typeahead/typeahead.html',
            restrict: 'E',
//            transclude: true,
//            replace: true,
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                http://suhairhassan.com/2013/05/01/getting-started-with-angularjs-directive.html#.WBpEIGqLSUl
                var vm = scope.vm;
                vm.items = null;
                vm.getItems = function (str) {
                    if (vm.items == null) {
                        vm.loadingItems = true;
                        return vm.loadItems(function (data) {
                            $log.info("loadItems");
                            vm.items = data;
                            vm.loadingItems = false;
                            $timeout(function () {
                                var v = scope.vm.editForm.formInput.$viewValue;
                                scope.vm.editForm.formInput.$setViewValue('');
                                $timeout(function () {
                                    scope.vm.editForm.formInput.$setViewValue(v);
                                }, 0);
                            }, 0);
                            return vm.filter(str);
                        });
                    }
                    return vm.filter(str);
                };

                vm.filter = function(str) {
                    var result = [];
                    angular.forEach(vm.items, function(item) {
                        if (typeof str == 'undefined' || (item.name).toLowerCase().indexOf(('' + str).toLowerCase()) > -1) {
                            result.push(item);
                        }
                    });
                    $log.info("Str = " + str + ", Filtered: " + result);
                    return result;
                };

            }
        }
    }
})();
