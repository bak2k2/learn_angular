'use strict';

function cloneData(objectToClone)
{
    return JSON.parse(JSON.stringify(objectToClone));
}

function ProjectHomeCtrl($scope, ProjectGateway, ProjectsGateway) {
    $scope.editMode = false;
    $scope.projects = ProjectsGateway.query();

    $scope.select = function(id){
        var resp = ProjectGateway.get({id: id}, function(project){
            $scope.project = project;
            setEditMode(false);
        });
    }

    $scope.edit = function(){
        setEditMode(true);
    }

    $scope.cancel = function(){
        setEditMode(false);
    }

    $scope.save = function(){
        ProjectGateway.save({id: $scope.editable_project.id}, $scope.editable_project, onSave);
    }

    function setEditMode(isEditMode){
        $scope.editMode = isEditMode;
        $scope.editable_project = cloneData($scope.project);
    }

    function onSave(project){
        $scope.editMode = false;
        $scope.project = project;
        $scope.editable_project = cloneData(project);
        $scope.$broadcast('metricsApp.projectSaved');
    }

    $scope.$on('metricsApp.projectSaved', function(){
        $scope.projects = ProjectsGateway.query();
    });
}

