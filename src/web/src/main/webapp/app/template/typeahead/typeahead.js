(function () {
    'use strict';

    angular
        .module('app')
        .directive('typeAhead', TypeAhead);

    TypeAhead.$inject = ['$log', '$timeout', '$filter', '$q'];

    function TypeAhead($log, $timeout, $filter, $q) {
        return {
            templateUrl: 'app/template/typeahead/typeahead.html',
            restrict: 'E',
            transclude: true,
//             replace: true,
            scope: {
                ngModel: '=',
                loadItems: '=',
                placeHolder: '=',
                getComboItemPrefix: '=',
                fixedElement: '=',
                filter: '=',
                templateUrl: '='
            },
            // require:"ngModel",
            link: function(scope, element, attrs, controller) {
                //http://suhairhassan.com/2013/05/01/getting-started-with-angularjs-directive.html#.WBpEIGqLSUl
//                var formInput = angular.element(element[0].firstChild.firstChild.nextElementSibling).controller("ngModel");
                // angular.element(element[0].firstChild.firstChild.nextElementSibling).controller("uibTypeahead")
                var filter = function(str, items) {
                    if (scope.filter != undefined) {
                        return scope.filter(str, items);
                    }
                    var result = [];
                    angular.forEach(items, function(item) {
                        if (typeof str == 'undefined' || (item.name).toLowerCase().indexOf(('' + str).toLowerCase()) > -1) {
                            result.push(item);
                        }
                    });
                    // $log.info("Str = " + str + ", Filtered: " + result);
                    return result;
                };
                if (scope.templateUrl == undefined) {
                    scope.templateUrl = 'uib/template/typeahead/mascot-typeahead-match.html'
                }
                scope.items = null;
                scope.getItems = function (str) {
                    if (scope.items == null) {
                        scope.loadingItems = true;
                        var deferred = $q.defer();
                        scope.loadItems(function(data) {
                            scope.items = data;
                            scope.loadingItems = false;
                            deferred.resolve(filter(str, data));
                        });
                        return deferred.promise;
                    }
                    return filter(str, scope.items);
                };


                scope.onSelectItem = function(item) {
                    $log.info("Selected: " + item);
                    scope.ngModel = item;
                };

                scope.getPrefix = function(item) {
                    if (scope.getComboItemPrefix == undefined) {
                        return "";
                    }

                    return '[' + scope.getComboItemPrefix(item) + ']';
                };

                scope.isCurrent = function(item) {
                    // $log.info("AAAAAAA: " + (item == scope.ngModel));
                    return item == scope.ngModel;
                };

                scope.setFixed = function() {
                    scope.ngModel = scope.fixedElement;
                }

            }
        }
    }
})();
