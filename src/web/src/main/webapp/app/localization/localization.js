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
            var result = {
                /** first argument is key of message.
                 * Other arguments for replace {0}, {1} ... **/
                get: function () {
                    var result = findMessage(arguments[0]);
                    var args = buildReplaceArgs(arguments);
                    return format(result, args);
                }
            };
            var format = function (text, args) {
                return text.replace(/\{(\d+)\}/g, function (m, n) {
                    return args[n] ? args[n] : m;
                });
            };
            var findMessage = function(text) {
                if (localizedTexts.hasOwnProperty(text)) {
                    return localizedTexts[text];
                }
                return text;
            };

            var buildReplaceArgs = function(replaceArray) {
                var args = [];
                for (var i = 1; i < replaceArray.length; i++) {
                    args[i - 1] = replaceArray[i];
                }
                return args;
            };
            return result;
        }])
})();