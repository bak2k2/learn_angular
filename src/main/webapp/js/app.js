'use strict';

var metricsModule = angular.module('metrics', ['ngResource']);

metricsModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/admin', {templateUrl: 'partials/admin/admin_home.html',   controller: AdminHomeCtrl}).
        when('/admin/iterations', {templateUrl: 'partials/admin/iterations.html',   controller: AdminHomeCtrl}).
        when('/projects', {templateUrl: 'partials/project/project_home.html',   controller: ProjectHomeCtrl}).
        otherwise({redirectTo: '/projects'});
}]);
