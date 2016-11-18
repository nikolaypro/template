(function () {
    'use strict';

    angular
        .module('mascotCommon', ['localization'])
        .factory('CommonUtils', CommonUtils);

    CommonUtils.$inject = ['$log', '$rootScope', 'LocMsg'];
    function CommonUtils($log, $rootScope, LocMsg) {
        var service = {};
        service.getDateFormat = getDateFormat;
        service.getCurrDateWOTime = getCurrDateWOTime;
        service.getStartWeek = getStartWeek;
        service.getEndWeek = getEndWeek;
        return service;

        function getDateFormat() {
            return $rootScope.globals.currentUser.dateFormat;
        }

        function getCurrDateWOTime() {
            var d = new Date();
            d.setHours(0,0,0,0);
            return d;
        }

        function getStartWeek(date) {
            if (date == undefined) {
                return undefined;
            }
            var first = date.getDay() == 0 ? (date.getDate() - 6) : (date.getDate() - date.getDay() + 1); // First day is the day of the month - the day of the week

            return new Date(new Date(date).setDate(first));
        }
        function getEndWeek(date) {
            if (date == undefined) {
                return undefined;
            }
            var startWeek = getStartWeek(date);
            var last = startWeek.getDate() + 6;
            return new Date(new Date(startWeek).setDate(last));
        }

    }

})();