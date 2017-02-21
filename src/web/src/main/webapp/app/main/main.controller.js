(function () {
    'use strict';

    angular
        .module('app')
        .controller('MainController', MainController);

    MainController.$inject = ["$location", "Utils"];
    function MainController($location, Utils) {
        // $location.path('/' + Utils.getEnabledMenu()[0])
    }

})();
