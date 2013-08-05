'use strict';

function cloneData(obj)
{
    return JSON.parse(JSON.stringify(obj));
}

function ProjectListCtrl($scope, Project, Projects) {

    $scope.editMode = false;

    $scope.projects = Projects.query();

    $scope.select = function(id){
        var resp = Project.get({id: id}, function(project){
            $scope.project = project;
            $scope.editable_project = cloneData($scope.project);
        });
    }

    $scope.edit = function(){
        $scope.editMode = !$scope.editMode;
        if ($scope.editMode)
            $scope.editable_project = cloneData($scope.project);
    }

}

