'use strict';

var metricsModule = angular.module('metrics', ['ngResource']);

metricsModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/admin', {templateUrl: 'partials/admin_list.html',   controller: ProjectHomeCtrl}).
        when('/projects', {templateUrl: 'partials/project/project_home.html',   controller: ProjectHomeCtrl}).
        otherwise({redirectTo: '/projects'});
}]);
