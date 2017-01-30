(function () {
    'use strict';

    angular
        .module('app')
        .factory('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$http', '$cookieStore', '$rootScope', '$timeout', 'UserService', 'UrlService', 'Utils'];
    function AuthenticationService($http, $cookieStore, $rootScope, $timeout, UserService, UrlService, Utils) {
        var service = {};

        service.Login = Login;
        service.Logout = Logout;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;
        service.AutoLogin = AutoLogin;

        return service;

        function Login(username, password, callback) {

            /* Dummy authentication for testing, uses $timeout to simulate api call
             ----------------------------------------------*/
/*
            $timeout(function () {
                var response;
                UserService.getByUsername(username)
                    .then(function (user) {
                        if (user !== null && user.password === password) {
                            response = { success: true };
                        } else {
                            response = { success: false, message: 'Username or password is incorrect' };
                        }
                        callback(response);
                    });
            }, 1000);
*/

            /* Use this for real authentication
             ----------------------------------------------*/
            var url = UrlService.url('api/authenticate');
            var encodedPassword = UserService.base64().encode(UserService.encode_utf8(password));
            $http.post(url, { login: username, password: encodedPassword })
                .success(function (response) {
                    response.success = true;
                    callback(response);
                })
                .error(function (data, status, headers, config) {
                    var message = 'Unknown error';
                    if (status == 404) {
                        message = 'Server not found';
                    }
                    if (status == 401 && data.error && data.error.indexOf('trial version') != -1 ) {
                        message = data.error;
                    } else if (status == 401 && data.error) {
                        message = 'Username or password is incorrect';
                    }
                    var response = { success: false, message: message };
                    callback(response);
                });

        }

        function SetCredentials(username, password, response) {
            var roles = response.roles;
            var locale = response.locale;
            var appVersion = response.appVersion;
            var productAutocompleteType = response.productAutocompleteType;
            var reportGroupEnabled = response.reportGroupEnabled;

            var authdata = UserService.base64().encode(UserService.encode_utf8(username) + ':' + UserService.encode_utf8(password));

            $rootScope.globals = {
                currentUser: {
                    username: username,
                    authdata: authdata,
                    roles: roles,
                    locale: locale,
                    dateFormat: 'yyyy-MM-dd'
                },
                settings: {
                    productAutocompleteType: productAutocompleteType,
                    reportGroupEnabled: reportGroupEnabled
                }
            };

            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $rootScope.globals.appVersion = appVersion;
            $cookieStore.put('globals', $rootScope.globals);
            Utils.updateDocumentTitle();
        }

        function Logout(callback) {
            $http.post(UrlService.url('/logout'), {})
                .success(function (response) {
                    callback(true)
                })
                .error(function (data, status, headers, config) {
                    callback(false)
                });
        }

        function AutoLogin(callback) {
            $http.post(UrlService.url('api/autoLogin'), {})
                .success(function (response) {
                    callback(response)
                })
                .error(function (data, status, headers, config) {
                    callback({enabled: false})
                });
        }

        function ClearCredentials() {
            $rootScope.globals = {};
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
        }
    }

})();