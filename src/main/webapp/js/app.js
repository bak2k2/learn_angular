'use strict';

/* App Module */

angular.module('metrics', ['projectServices']).
    config(['$routeProvider', function($routeProvider) {
        $routeProvider.
            when('/admin', {templateUrl: 'partials/admin_list.html',   controller: ProjectListCtrl}).
            when('/projects', {templateUrl: 'partials/project_list.html',   controller: ProjectListCtrl}).
            when('/project/:projectId', {templateUrl: '/partials/project_list.html', controller: ProjectDetailCtrl}).
            otherwise({redirectTo: '/projects'});
    }]);
