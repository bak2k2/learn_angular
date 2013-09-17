'use strict';

var metricsModule = angular.module('metrics', ['ngResource', 'restangular']);

metricsModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/admin', {templateUrl: 'partials/admin/home.html',   controller: AdminHomeCtrl}).
        when('/dash', {templateUrl: 'dash.html'}).
        when('/dashboard', {templateUrl: 'partials/dashboard/home.html'}).
        when('/admin/iterations', {templateUrl: 'partials/admin/iterations.html',   controller: AdminHomeCtrl}).
        when('/admin/projects', {templateUrl: 'partials/admin/projects.html',   controller: AdminHomeCtrl}).
        when('/projects', {templateUrl: 'partials/project/home.html',   controller: ProjectHomeCtrl}).
        when('/reports', {templateUrl: 'partials/reports/home.html',   controller: ReportsHomeCtrl}).
        otherwise({redirectTo: '/projects'});
}]);

metricsModule.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl('/resources');
});