'use strict'

function ProjectHomeCtrl($scope, ProjectGateway, ProjectsGateway, IterationsGateway, ProjectIterationGateway) {
    $scope.editMode = false;
    $scope.selectedIterationId = {};

    $scope.iterations = {};

    IterationsGateway.query(function(iterations){
        $scope.iterations = iterations;
    });

    ProjectsGateway.query(function(projects){
        $scope.projects = projects;
        if (projects.length > 0){
            $scope.select(projects[0].id);
        }
    });

    $scope.selectIteration = function(){
        alert(JSON.stringify($scope.selectedIterationId));
        ProjectIterationGateway.get({projectId: $scope.project.id, iterationId: $scope.selectedIterationId},
            function(projIterationDetails){
                $scope.projectIterationDetails = projIterationDetails;
            });
        //$scope.projectIterationDetails = {velocity: "10"}
    }

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

    $scope.applyIterationChanges = function(){

    }

    function onSave(project){
        $scope.project = project;
        setEditMode(false);
        $scope.$broadcast('metricsApp.projectSaved');
    }

    function setEditMode(isEditMode){
        $scope.editMode = isEditMode;
        $scope.editable_project = cloneData($scope.project);
    }

    $scope.$on('metricsApp.projectSaved', function(){
        $scope.projects = ProjectsGateway.query();
    });

}