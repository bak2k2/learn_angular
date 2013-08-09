'use strict';

var metricsModule = angular.module('metrics', ['ngResource', 'metrics.directives']);

metricsModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/admin', {templateUrl: 'partials/admin/home.html',   controller: AdminHomeCtrl}).
        when('/dashboard', {templateUrl: 'partials/dashboard/home.html', controller: DashboardCtrl}).
        when('/admin/iterations', {templateUrl: 'partials/admin/iterations.html',   controller: AdminHomeCtrl}).
        when('/admin/projects', {templateUrl: 'partials/admin/projects.html',   controller: AdminHomeCtrl}).
        when('/projects', {templateUrl: 'partials/project/home.html',   controller: ProjectHomeCtrl}).
        otherwise({redirectTo: '/dashboard'});
}]);
