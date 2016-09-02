﻿(function () {
    'use strict';

    angular
        .module('app')
        .factory('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$http', '$cookieStore', '$rootScope', '$timeout', 'UserService', 'UrlService'];
    function AuthenticationService($http, $cookieStore, $rootScope, $timeout, UserService, UrlService) {
        var service = {};

        service.Login = Login;
        service.Logout = Logout;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;

        return service;

        function Login(username, password, callback) {

            /* Dummy authentication for testing, uses $timeout to simulate api call
             ----------------------------------------------*/
/*
            $timeout(function () {
                var response;
                UserService.GetByUsername(username)
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
            var encodedPassword = UserService.Base64.encode(password);
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
                    if (status == 401 && data.error) {
                        message = 'Username or password is incorrect';
                    }
                    var response = { success: false, message: message };
                    callback(response);
                });

        }

        function SetCredentials(username, password, roles) {
            var authdata = UserService.Base64.encode(username + ':' + password);

            $rootScope.globals = {
                currentUser: {
                    username: username,
                    authdata: authdata,
                    roles: roles
                }
            };

            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
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

        function ClearCredentials() {
            $rootScope.globals = {};
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
        }
    }

})();