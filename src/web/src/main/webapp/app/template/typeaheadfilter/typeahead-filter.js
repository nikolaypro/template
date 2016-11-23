(function () {
    'use strict';

    angular
        .module('app')
        .directive('typeAheadFilter', TypeAheadFilter);

    TypeAheadFilter.$inject = ['$log', '$q'];

    function TypeAheadFilter($log, $q) {
        return {
            templateUrl: 'app/template/typeaheadfilter/typeahead-filter.html',
            restrict: 'E',
            transclude: true,
//             replace: true,
            scope: {
                vm: '=',
                loadItems: '=',
                ngModel: '='
            },
//            require:"ngModel",
            link: function(scope, element, attrs, controller) {
                var vm = scope.vm;
                var items = null;
                var filter = function(str, items) {
                    var result = [];
                    angular.forEach(items, function(item) {
                        if (item.toLowerCase().indexOf(('' + str).toLowerCase()) > -1) {
                            result.push(item);
                        }
                    });
                    return result;
                };

                scope.getItems = function (str) {
                    if (items == null) {
                        var deferred = $q.defer();
                        scope.loadItems().then(function(data) {
                            items = data;
                            deferred.resolve(filter(str, data));
                        });
                        return deferred.promise;
                    }
                    return filter(str, items);
                }
            }
        }
    }
})();
