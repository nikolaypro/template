(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies', 'ngTable', 'ngDialog', 'localization', 'ui.bootstrap', 'mascotCommon'])
        .constant('ALL_APP_ROLES', {
            admin: 'ROLE_ADMIN',
            regular: 'ROLE_REGULAR'
        })
        .factory('httpInterceptor', ['$log', 'FlashService', '$q', 'LongRunService', 'UrlService', '$location', function($log, FlashService, $q, LongRunService, UrlService, $location) {
            $log.debug('$log is here to show you that this is a regular factory with injection');

            var isLong = function(url) {
                return UrlService.isApiUrl(url) && UrlService.isShowLongRequest(url);
            };
            var httpInterceptor = {
                request: function(config) {
//                    $log.debug('!!!!!!!! - request - !!!!!!!!');
                    if (isLong(config.url)) {
                        LongRunService.startLong();
                    }
                    return config;
                },
                response: function(response) {
//                    $log.debug('!!!!!!!! - response - !!!!!!!!');
                    if (isLong(response.config.url)) {
                        LongRunService.endLong();
                    }
                    return response;
                },
                responseError: function(rejection) {
                    // do something on error
//                    $log.debug('!!!!!!!! - response error - !!!!!!!!');
                    if (isLong(rejection.config.url)) {
                        LongRunService.endLong();
                    }
                    if (rejection.status == 403) {
                        $location.path('/login');
                    } else {
                        FlashService.Error(rejection.data.error);
                    }

                    return $q.reject(rejection);
                }
            };

            return httpInterceptor;
        }])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$httpProvider'];
    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                controller: 'MainController',
//                templateUrl: 'home/home.view.html',
                templateUrl: 'app/main/main.view.html',
                controllerAs: 'vm'
            })
            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'app/login/login.view.html',
                controllerAs: 'vm'
            })
            .when('/home', {
                controller: 'MainController',
//                templateUrl: 'home/home.view.html',
                templateUrl: 'app/main/main.view.html',
                controllerAs: 'vm'
            })
            .when('/products', {
                controller: 'ProductsController',
                templateUrl: 'app/dictionary/product/product.view.html',
                controllerAs: 'vm'
            })
            .when('/orders', {
                controller: 'OrdersController',
                templateUrl: 'app/order/orders.view.html',
                controllerAs: 'vm'
            })
            .when('/new_product', {
                controller: 'OrderProductController',
                templateUrl: 'app/order/product/order.product.view.html',
                controllerAs: 'vm'
            })
            .when('/order-product', {
                controller: 'OrderProductController',
                templateUrl: 'app/order/product/order.product.view.html',
                controllerAs: 'vm'
            })
            .otherwise({ redirectTo: '/login' });

        $httpProvider.interceptors.push('httpInterceptor');
/*
        $httpProvider.interceptors.push(function($q, dependency1, dependency2) {
            return {
                'responseError': function(rejection) {
                    FlashService.Error(rejection);
                    // do something on error
                    return $q.reject(rejection);
                }
            };
        });
*/

    }

    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http', 'ALL_APP_ROLES', '$templateCache', 'Utils'];
    function run($rootScope, $location, $cookieStore, $http, ALL_APP_ROLES, $templateCache, Utils) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
        Utils.updateDocumentTitle();
        $rootScope.userPerm = {
            users: function () {
                return ($rootScope.globals.roles.contains(ALL_APP_ROLES.admin));
            }
        };
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });

        $templateCache.put("uib/template/typeahead/mascot-typeahead-match.html",
            " <a>"+
            "   <span class='typeahead-prefix'>{{$parent.$parent.$parent.getPrefix(match.model)}}</span>" +
            "   <span ng-bind-html=\"match.label | uibTypeaheadHighlight:query\"></span>" +
            " </a>"

        );

        $templateCache.put("uib/template/typeahead/mascot-typeahead-wo-match.html",
            " <a>"+
            "   <span class='typeahead-prefix'>{{$parent.$parent.$parent.getPrefix(match.model)}}</span>" +
            "   <span>{{match.label}}</span>" +
            " </a>"

        );

    }

})();