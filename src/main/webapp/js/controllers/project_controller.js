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
        $scope.editMode = true;
        $scope.editable_project = cloneData($scope.project);
    }
    $scope.save = function(){
        Project.save({id: $scope.editable_project.id}, $scope.editable_project, onSave);
    }

    $scope.cancel = function(){
        $scope.editMode = false;
        $scope.editable_project = cloneData($scope.project);
    }

    function onSave(project){
        $scope.editMode = false;
        $scope.project = project;
        $scope.editable_project = cloneData(project);
        $scope.$broadcast('metricsApp.projectSaved');
    }

    $scope.$on('metricsApp.projectSaved', function(){
        $scope.projects = Projects.query();
    });
}

