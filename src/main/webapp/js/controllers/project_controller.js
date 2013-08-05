'use strict';

function ProjectListCtrl($scope, Project, Projects) {

    $scope.projects = Projects.query();

    $scope.greet = function(id){
        var resp = Project.get({id: id}, function(project){
            $scope.project = project;
        });
    }

    $scope.edit = function(){
        $scope.editMode = !$scope.editMode;
    }

    $scope.editMode = false;
}

