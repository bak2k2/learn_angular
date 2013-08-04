'use strict';

/* Controllers */

function ProjectListCtrl($scope, Project, Projects) {
    $scope.projects = Project.query();
    $scope.greet = function(id){
        var resp = Projects.get({id: id}, function(project){
            alert(project.projectName);
        });
    }
}


function ProjectDetailCtrl($scope, $routeParams, Project) {
    $scope.project = Project.get({projectId: $routeParams.projectId}, function(project) {
        $scope.mainImageUrl = project.images[0];
    });
}

