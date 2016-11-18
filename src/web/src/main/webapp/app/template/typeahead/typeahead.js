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
           transclude: true,
//             replace: true,
            scope: {
                vm: '=info',
                modelValue: '=',
                loadItems: '='
            },
            // require:"ngModel",
            link: function(scope, element, attrs, controller) {
                http://suhairhassan.com/2013/05/01/getting-started-with-angularjs-directive.html#.WBpEIGqLSUl
                var vm = scope.vm;
                var formInput = angular.element(element[0].firstChild.firstChild.nextElementSibling).controller("ngModel");
                // angular.element(element[0].firstChild.firstChild.nextElementSibling).controller("uibTypeahead")
                vm.items = null;
                vm.getItems = function (str) {
                    if (vm.items == null) {
                        vm.loadingItems = true;
                        return scope.loadItems(function (data) {
                            $log.info("loadItems");
                            vm.items = data;
                            angular.forEach(vm.items, function(item) {
                                if (item.$fullItemName == undefined) {
                                    if (vm.itemFormatter == undefined) {
                                        item.$fullItemName = item.name;
                                    } else {
                                        item.$fullItemName = vm.itemFormatter(item);
                                    }
                                }
                            });
                            vm.loadingItems = false;
                            // var formInput = vm.editForm[vm.elementId];
                            $timeout(function () {
                                var v = formInput.$viewValue;
                                formInput.$setViewValue('');
                                $timeout(function () {
                                    formInput.$setViewValue(v);
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

                vm.onSelectItem = function(item) {
                    $log.info("Selected: " + item);
                    scope.modelValue = item;
                };


            }
        }
    }
})();
