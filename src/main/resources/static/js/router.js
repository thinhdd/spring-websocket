(function () {
    'use strict';

    angular.module('myApp')
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($urlRouterProvider, $stateProvider) {
        //$urlRouterProvider.otherwise('/login');

        $stateProvider
            .state('app', {
                url: '/app',
                templateUrl: 'app.html',
                abstract: true
            })
            //todo should move to state config for authentication
            .state('app.login', {
                url: '/login',
                controller: 'LoginCtrl',
                controllerAs: 'vm',
                templateUrl: 'login.html',
                title: 'Sign In'
            })
            .state('app.trade', {
                url: '/trade',
                controller: 'tradeCtrl',
                controllerAs: 'vm',
                templateUrl: 'trade.html',
                title: 'Trade'
            });
        $urlRouterProvider
            .otherwise('login');
    }

})();
