'use strict';

/* Controllers */

function ProjectListCtrl($scope, Project) {
    $scope.projects = Project.query();
}


function ProjectDetailCtrl($scope, $routeParams, Project) {
    $scope.project = Project.get({projectId: $routeParams.projectId}, function(project) {
        $scope.mainImageUrl = project.images[0];
    });
}

