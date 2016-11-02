(function () {
    'use strict';

    angular
        .module('app')
        .directive('typeAhead', TypeAhead);

    TypeAhead.$inject = [];

    function TypeAhead() {
        return {
            templateUrl: 'app/template/typeahead/typeahead.html',
            restrict: 'E',
//            transclude: true,
//            replace: true,
            scope: {
                vm: '=info'
            },
            link: function(scope, element, attrs, tabsCtrl) {
                var vm = scope.vm;
            }
        }
    }
})();
