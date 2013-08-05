'use strict';

var metricsModule = angular.module('metrics', ['ngResource']);

metricsModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/admin', {templateUrl: 'partials/admin_list.html',   controller: ProjectListCtrl}).
        when('/projects', {templateUrl: 'partials/project_list.html',   controller: ProjectListCtrl}).
        otherwise({redirectTo: '/projects'});
}]);
