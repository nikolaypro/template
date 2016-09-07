(function () {
    'use strict';

    angular.module('localization', [])
    .filter('i18n', ['LocMsg', function (LocMsg) {

            return function (text) {
                return LocMsg.get(text);
            };
/*
        return function (text) {
            if (localizedTexts.hasOwnProperty(text)) {
                return localizedTexts[text];
            }
            return text;
        };
*/
    }]).factory('LocMsg', ['localizedTexts', function(localizedTexts) {
        return {
            get : function(text) {
                if (localizedTexts.hasOwnProperty(text)) {
                    return localizedTexts[text];
                }
                return text;
            }
        }

    }])
})();